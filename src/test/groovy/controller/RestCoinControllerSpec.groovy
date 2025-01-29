package controller

import com.crypto.app.tracker.controller.RestCoinController
import com.crypto.app.tracker.models.CoinData
import com.crypto.app.tracker.service.CoinRestService
import spock.lang.Specification

class RestCoinControllerSpec extends Specification{
    RestCoinController fixture
    def coinService

  def setup() {
       coinService = Mock(CoinRestService)
       fixture = [ coinService: coinService]as RestCoinController
  }
    def "controller is called"(){
        given:
        def coinName = "DOGE"
        def coinData = [usd: 1.00, usdMarketCap:1.2, usd24hVolume:99.52, usd24hChange:3.637,lastUpdated:171135630] as CoinData
        when:
        def result = fixture.getCoins(coinName)
        then:
        result ==[ coinData]
        1 * coinService.getCoinData(coinName) >> coinData
    }
}
