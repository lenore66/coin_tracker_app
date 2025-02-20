package client


import com.crypto.app.tracker.client.impl.CoinMetaDataClientImpl
import com.crypto.app.tracker.models.metadata.Coin

import com.crypto.app.tracker.models.metadata.Description
import com.crypto.app.tracker.models.metadata.Metadata
import com.crypto.app.tracker.models.metadata.Ticker
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

import static com.crypto.app.tracker.constants.CoinMarketApiConstants.COIN_SCHEME
import static com.crypto.app.tracker.constants.CoinMetaDataApiConstants.COIN_GECKO_HOST
import static com.crypto.app.tracker.constants.CoinMetaDataApiConstants.COIN_GECKO_KEY
import static com.crypto.app.tracker.constants.CoinMetaDataApiConstants.COIN_GECKO_PATH_LIST
import static com.crypto.app.tracker.constants.CoinMetaDataApiConstants.COIN_GECKO_PATH_METADATA

class CoinMetaDataClientImplSpec extends Specification {
    def mockTemplate;
    def fixture = [] as CoinMetaDataClientImpl
    def description = [englishDescription: "yay"] as Description
    def tickers = [trust_score: "green", tickersBase: "ASEFS"] as Ticker
    def setup() {
        mockTemplate = Mock(RestTemplate)
        fixture.restTemplate = mockTemplate

    }

    def "the coinDataClient is called and returns CoinData if api recives a name of coin"(){
        given:
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("x-cg-demo-api-key", COIN_GECKO_KEY);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        def coinData = [
                name       : "bitcoin",
                symbol     : "BTC",
                description: description,
                logo       : "https://example.com/bitcoin-logo.png",
                tickers    : [tickers],
        ] as Metadata
        def coinName = "bitcoin"

        def coinListUrl = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME)
                .host(COIN_GECKO_HOST)
                .path(COIN_GECKO_PATH_LIST)
                .build()
                .toUriString();
        def coinList = [
                [coinName: "Bitcoin".toLowerCase(),
                 coinId:"btc",
                 symbol: "btc"] as Coin
        ] as List


        def url = UriComponentsBuilder.newInstance().scheme(COIN_SCHEME).host(COIN_GECKO_HOST).path(COIN_GECKO_PATH_METADATA.concat("/").concat(coinName)).build().toUriString()

        when:
        def result = fixture.getCoinMetaData(coinName)

        then:
        1 * mockTemplate.exchange(coinListUrl, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Coin>>() {}) >> new ResponseEntity<>(coinList, HttpStatus.OK)
        1 * mockTemplate.exchange(url, HttpMethod.GET, httpEntity, Metadata.class) >> new ResponseEntity<>(coinData, HttpStatus.OK)
    }

    def "the coinDataClient is called and returns CoinData if api recives a ticker of coin"() {
        given:
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("x-cg-demo-api-key", COIN_GECKO_KEY);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        def coinData = [
                name       : "bitcoin",
                symbol     : "btc",
                description: description,
                logo       : "https://example.com/bitcoin-logo.png",
                tickers    : [tickers]
        ] as Metadata
        def ticker = "\$btc"

        def coinListUrl = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME)
                .host(COIN_GECKO_HOST)
                .path(COIN_GECKO_PATH_LIST)
                .build()
                .toUriString();
        def coinList = [
                   [coinName: "Bitcoin",
                    coinId:"btc",
                   symbol: "btc"] as Coin
                ] as List

        def url = UriComponentsBuilder.newInstance().scheme(COIN_SCHEME).host(COIN_GECKO_HOST).path(COIN_GECKO_PATH_METADATA.concat("/").concat(coinData.symbol)).build().toUriString()


        when:
        def result = fixture.getCoinMetaData(ticker)

        then:

        1 * mockTemplate.exchange(coinListUrl, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Coin>>() {}) >> new ResponseEntity<>(coinList, HttpStatus.OK)
        1 * mockTemplate.exchange(url, HttpMethod.GET, httpEntity, Metadata.class) >> new ResponseEntity<>(coinData, HttpStatus.OK)
        result ==coinData
    }
}
