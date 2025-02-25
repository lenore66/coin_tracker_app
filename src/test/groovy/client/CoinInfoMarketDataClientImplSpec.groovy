package client

import com.crypto.app.tracker.client.impl.CoinMarketDataClientImpl
import com.crypto.app.tracker.models.marketdata.MarketData
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification
import org.springframework.http.HttpStatus


import static com.crypto.app.tracker.constants.CoinMarketApiConstants.COIN_GECKO_HOST
import static com.crypto.app.tracker.constants.CoinMarketApiConstants.COIN_GECKO_PATH
import static com.crypto.app.tracker.constants.CoinMarketApiConstants.COIN_SCHEME
import static com.crypto.app.tracker.constants.CoinMetaDataApiConstants.COIN_GECKO_KEY

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
        headers.add("x-cg-demo-api-key", COIN_GECKO_KEY);
        HttpEntity httpEntity  = new HttpEntity<>(headers);

        def coinName = "bitcoin"
        def coinData =[[current_price: 1.00, market_cap:1.2, fully_diluted_valuation:99.52, total_volume:3.637,low_24h:171135630 ] as MarketData]
        def coinUrl =  UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_GECKO_HOST).path(COIN_GECKO_PATH).queryParam("ids",coinName)
                .queryParam("vs_currency",currency).build().toUriString();

        when:
        def result = fixture.getCoinData(coinName, currency)

        then:

      1 * restTemplate.exchange(coinUrl, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<MarketData>>() {}) >> new ResponseEntity<>(coinData, HttpStatus.OK)
        result == coinData[0]
    }
}
