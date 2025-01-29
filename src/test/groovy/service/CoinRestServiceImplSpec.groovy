package service

import com.crypto.app.tracker.client.CoinDataClient
import com.crypto.app.tracker.models.CoinData
import com.crypto.app.tracker.service.impl.CoinRestServiceImpl
import spock.lang.Specification

class CoinRestServiceImplSpec extends Specification {
    CoinRestServiceImpl fixture
    def coinDataClient;
    def setup(){
        coinDataClient = Mock(CoinDataClient)
        fixture = [coinDataClient: coinDataClient] as CoinRestServiceImpl
    }
    def "the coinDataClient is called and returns CoinData"(){
        given:
        def coinName = "bitcoin"
        def coinData = [usd: 1.00, usdMarketCap:1.2, usd24hVolume:99.52, usd24hChange:3.637,lastUpdated:171135630] as CoinData
        when:
        def result = fixture.getCoinData(coinName)
        then:
        result == coinData
        1 * coinDataClient.getCoinData(coinName) >> coinData
    }
}
