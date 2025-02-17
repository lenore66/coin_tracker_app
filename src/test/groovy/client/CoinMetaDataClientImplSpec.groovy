package client


import com.crypto.app.tracker.client.impl.CoinMetaDataClientImpl
import com.crypto.app.tracker.models.metadata.Coin
import com.crypto.app.tracker.models.metadata.CoinList
import com.crypto.app.tracker.models.metadata.Description
import com.crypto.app.tracker.models.metadata.Metadata
import com.crypto.app.tracker.models.metadata.Ticker
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

class CoinMetaDataClientImplSpec extends Specification {
    def mockTemplate;
    def fixture

    def setup() {
        mockTemplate = Mock(RestTemplate)
        fixture = [restTemplate: mockTemplate] as CoinMetaDataClientImpl
    }

//    def "the coinDataClient is called and returns CoinData if api recives a name of coin"(){
//        given:
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
//        headers.add("X-CMC_PRO_API_KEY",X_CMC_PRO_API_KEY);
//        HttpEntity httpEntity = new HttpEntity<>(headers);
//
//        def coinName = "Bitcoin"
//        def coinData = [coinInfo: [ metaData: [ name: "bitcoin", symbol: "BTC", description: "crypto", logo: "http://abc.com"] as Metadata] as CoinInfo ] as CoinMetaData
//        def url = UriComponentsBuilder.newInstance()
//                .scheme(COIN_SCHEME).host(COIN_MARKET_CAP_HOST).path(COIN_METADATA_URL_PATH).queryParam("slug", coinName.toLowerCase()).build().toUriString();
//        when:
//        def result = fixture.getCoinMetaData(coinName)
//
//        then:
//        1 * mockTemplate.exchange(url, HttpMethod.GET, httpEntity,  CoinMetaData.class) >> new ResponseEntity<>(coinData, HttpStatus.OK)
//        result == coinData
//    }

    def "the coinDataClient is called and returns CoinData if api recives a ticker of coin"() {
        given:
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("x-cg-demo-api-key", COIN_GECKO_KEY);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        def ticker = "\$BTc"
        def fiatCurrency = "USD"
        def coinListUrl = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_GECKO_HOST).path(COIN_GECKO_PATH_LIST).build().toUriString();

        def url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_MARKET_CAP_HOST).path(COIN_METADATA_URL_PATH).queryParam("symbol", ticker.replace("\$", "").toLowerCase()).build().toUriString();
        def description = [englishDescription: "yay"] as Description
        def tickers = [trust_score: "green", tickersBase: "ASEFS"] as Ticker

        def coinData = [
                name       : "bitcoin",
                symbol     : "BTC",
                description: description,
                logo       : "https://example.com/bitcoin-logo.png",
                tickers    : [tickers],
        ] as Metadata


        def coinList = [
                coins: [[symbol: "BTC",
                         name  : "bitcoin",
                         id    : 'BTC'
                        ] as Coin] as CoinList
        ]

        when:
        def result = fixture.getCoinMetaData(ticker)

        then:
        1 * mockTemplate.exchange(url, HttpMethod.GET, httpEntity, Metadata.class) >> new ResponseEntity<>(coinData, HttpStatus.OK)
        1 * mockTemplate.exchange(coinListUrl, HttpMethod.Get, httpEntity, CoinList.class) >> new ResponseEntity<>(coinList, HttpStatus.OK)
        result == coinData
    }
}
