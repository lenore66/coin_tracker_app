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

        def coinName = "Bitcoin"
        def coinData = [usd: 1.00, usdMarketCap:1.2, usd24hVolume:99.52, usd24hChange:3.637,lastUpdated:171135630] as CoinMarketData
        def coinMetaData = [coinInfo: [ metadata: [ name: "Bitcoin", symbol: "BTC", description: "crypto", logo: "http://abc.com"] as Metadata] as CoinInfo ] as CoinMetaData
        def coinMarketMetaData = [coinMetaData: coinMetaData, coinMarketData: coinData] as CoinMetaMarketData

        when:
        def result = fixture.getCoinDataFromCoinName(coinName)

        then:

        1 * coinMetaDataClient.getCoinMetaData(coinName) >> coinMetaData
        1 * coinDataClient.getCoinData(coinMetaData.coinInfo.metadata.symbol) >> coinData
        result.coinMarketData == coinData
        result.coinMetaData == coinMetaData
    }
    def "the coinDataClient is called and gets the ticker symbol if a name of a coin is provided and then sends ticker"(){
        given:

        def coinTicker = "BTC"
        def coinData = [usd: 1.00, usdMarketCap:1.2, usd24hVolume:99.52, usd24hChange:3.637,lastUpdated:171135630] as CoinMarketData
        def coinMetaData = [coinInfo: [ metaData: [ name: "Bitcoin", symbol: "BTC", description: "crypto", logo: "http://abc.com"] as Metadata] as CoinInfo ] as CoinMetaData
        def coinMarketMetaData = [coinMetaData: coinMetaData, coinMarketData: coinData] as CoinMetaMarketData

        when:
        def result = fixture.getCoinsByTicker(coinTicker)

        then:

        1 * coinDataClient.getCoinData(coinTicker) >> coinData
        1 * coinMetaDataClient.getCoinMetaData(coinTicker) >> coinMetaData
        result.coinMarketData == coinData
        result.coinMetaData == coinMetaData
    }
}
