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
        def coinData = [] as CoinData
        when:
        def result = fixture.getCoins()
        then:
        //result ==[ coinData]
        1 * coinService.getCoinData() >> coinData
    }
}
