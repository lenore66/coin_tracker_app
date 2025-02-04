package controller

import com.crypto.app.tracker.controller.RestCoinController
import com.crypto.app.tracker.models.CoinMetaMarketData
import com.crypto.app.tracker.models.marketdata.CoinMarketData
import com.crypto.app.tracker.models.metadata.CoinInfo
import com.crypto.app.tracker.models.metadata.CoinMetaData
import com.crypto.app.tracker.models.metadata.Metadata
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
        def coinData = [usd: 1.00, usdMarketCap:1.2, usd24hVolume:99.52, usd24hChange:3.637,lastUpdated:171135630] as CoinMarketData
        def coinMetaData = [coinInfo: [ metaData: [ name: "Bitcoin", symbol: "BTC", description: "crypto", logo: "http://abc.com"] as Metadata] as CoinInfo ] as CoinMetaData
        def coinMarketMetaData = [coinMetaData: coinMetaData, coinMarketData: coinData] as CoinMetaMarketData
        when:
        def result = fixture.getCoins(coinName)
        then:
        result ==[ coinMarketMetaData]
        1 * coinService.getCoinDataFromCoinName(coinName) >> coinMarketMetaData
    }
}
