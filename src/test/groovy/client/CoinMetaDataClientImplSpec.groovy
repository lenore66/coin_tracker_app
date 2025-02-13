package client


import com.crypto.app.tracker.client.impl.CoinMetaDataClientImpl
import com.crypto.app.tracker.models.marketdata.CoinMarketData
import com.crypto.app.tracker.models.metadata.CoinInfo
import com.crypto.app.tracker.models.metadata.CoinMetaData
import com.crypto.app.tracker.models.metadata.Metadata
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

import static com.crypto.app.tracker.constants.CoinAPIConstants.COIN_MARKET_CAP_HOST
import static com.crypto.app.tracker.constants.CoinAPIConstants.COIN_METADATA_URL_PATH
import static com.crypto.app.tracker.constants.CoinAPIConstants.COIN_SCHEME
import static com.crypto.app.tracker.constants.CoinAPIConstants.X_CMC_PRO_API_KEY

class CoinMetaDataClientImplSpec extends  Specification {
    def mockTemplate;
    def fixture
    def setup(){
        mockTemplate = Mock(RestTemplate)
        fixture = [restTemplate: mockTemplate] as CoinMetaDataClientImpl
    }

    def "the coinDataClient is called and returns CoinData if api recives a name of coin"(){
        given:
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.CONTENT_TYPE,X_CMC_PRO_API_KEY);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        def coinName = "Bitcoin"
        def coinData = [coinInfo: [ metaData: [ name: "bitcoin", symbol: "BTC", description: "crypto", logo: "http://abc.com"] as Metadata] as CoinInfo ] as CoinMetaData
        def url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_MARKET_CAP_HOST).path(COIN_METADATA_URL_PATH).queryParam("slug", coinName.toLowerCase()).build().toUriString();
        when:
        def result = fixture.getCoinMetaData(coinName)

        then:
        1 * mockTemplate.exchange(url, HttpMethod.GET, httpEntity,  CoinMetaData.class) >> new ResponseEntity<>(coinData, HttpStatus.OK)
        result == coinData
    }
    def "the coinDataClient is called and returns CoinData if api recives a ticker of coin"(){
        given:
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.CONTENT_TYPE,X_CMC_PRO_API_KEY);
        HttpEntity httpEntity = new HttpEntity<>(headers);


       def ticker = "\$BTC"

        def coinData = [coinInfo: [ metaData: [ name: "bitcoin", symbol: "BTC", description: "crypto", logo: "http://abc.com"] as Metadata] as CoinInfo ] as CoinMetaData
        def url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_MARKET_CAP_HOST).path(COIN_METADATA_URL_PATH).queryParam("symbol", ticker).build().toUriString();
        when:
        def result = fixture.getCoinMetaData(ticker)

        then:
        1 * mockTemplate.exchange(url, HttpMethod.GET, httpEntity,  CoinMetaData.class) >> new ResponseEntity<>(coinData, HttpStatus.OK)
        result == coinData
    }
}
