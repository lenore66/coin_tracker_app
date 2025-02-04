package service

import com.crypto.app.tracker.client.CoinMarketDataClient
import com.crypto.app.tracker.client.CoinMetaDataClient
import com.crypto.app.tracker.models.CoinMetaMarketData
import com.crypto.app.tracker.models.marketdata.CoinMarketData
import com.crypto.app.tracker.models.metadata.CoinInfo
import com.crypto.app.tracker.models.metadata.CoinMetaData
import com.crypto.app.tracker.models.metadata.Metadata
import com.crypto.app.tracker.service.impl.CoinRestServiceImpl
import spock.lang.Specification

class CoinInfoRestServiceImplSpec extends Specification {
    CoinRestServiceImpl fixture
    def coinDataClient;
    def coinMetaDataClient

    def setup(){
        coinDataClient = Mock(CoinMarketDataClient)
        coinMetaDataClient = Mock(CoinMetaDataClient)
        fixture = [coinMarketDataClient: coinDataClient, coinMetaDataClient: coinMetaDataClient] as CoinRestServiceImpl
    }
    def "the coinDataClient is called and gets the ticker symbol if a name of a coin is provided and then sends ticker"(){
        given:

        def coinName = "btc"
        def coinData = [usd: 1.00, usdMarketCap:1.2, usd24hVolume:99.52, usd24hChange:3.637,lastUpdated:171135630] as CoinMarketData
        def coinMetaData = [coinInfo: [ metaData: [ name: "Bitcoin", symbol: "BTC", description: "crypto", logo: "http://abc.com"] as Metadata] as CoinInfo ] as CoinMetaData
        def coinMarketMetaData = [coinMetaData: coinMetaData, coinMarketData: coinData] as CoinMetaMarketData

        when:
        def result = fixture.getCoinDataFromCoinName(coinName)

        then:
        result == coinMarketMetaData
        1 * coinDataClient.getCoinDataFromCoinName(coinName) >> coinData
        1 * coinMetaDataClient.getCoinMetaData(coinName) >> coinMetaData
    }
}
