package client


import com.crypto.app.tracker.client.impl.CoinMarketDataClientImpl
import com.crypto.app.tracker.models.marketdata.CoinMarketData
import com.crypto.app.tracker.models.marketdata.MarketData
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification
import org.springframework.http.HttpStatus

import static com.crypto.app.tracker.constants.CoinMarketApiConstants.COIN_COMPARE_API_KEY
import static com.crypto.app.tracker.constants.CoinMarketApiConstants.COIN_DATA_URL_PATH
import static com.crypto.app.tracker.constants.CoinMarketApiConstants.COIN_EXCHANGE
import static com.crypto.app.tracker.constants.CoinMarketApiConstants.COIN_COMPARE_HOST
import static com.crypto.app.tracker.constants.CoinMarketApiConstants.COIN_SCHEME

class CoinInfoMarketDataClientImplSpec extends  Specification {
    def restTemplate;
    def fixture

    def setup(){

        restTemplate = Mock(RestTemplate)
        fixture = [restTemplate: restTemplate] as CoinMarketDataClientImpl
    }
    def "the coinDataClient is called and returns CoinData"(){
        given:
        def currency = "USD"
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity httpEntity  = new HttpEntity<>(headers);

        def coinName = "DOGE"
        def coinData =[ coinMarketData: [usd: 1.00, usdMarketCap:1.2, usd24hVolume:99.52, usd24hChange:3.637,lastUpdated:171135630] as CoinMarketData ] as MarketData
        def coinUrl = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_COMPARE_HOST).path(COIN_DATA_URL_PATH).queryParam("fsym", coinName).queryParam("e", COIN_EXCHANGE)
                .queryParam("tsym",currency).queryParam("api_key", COIN_COMPARE_API_KEY).build().toUriString();

        when:
        def result = fixture.getCoinData(coinName, currency)

        then:

      1 * restTemplate.exchange(coinUrl, HttpMethod.GET, httpEntity, MarketData.class) >> new ResponseEntity<>(coinData, HttpStatus.OK)
        result == coinData.coinMarketData
    }
}
