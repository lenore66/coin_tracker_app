package service

import com.crypto.app.tracker.client.CoinMarketDataClient
import com.crypto.app.tracker.client.CoinMetaDataClient
import com.crypto.app.tracker.mapper.CoinMarkerMetadataMapper
import com.crypto.app.tracker.models.CoinMetaMarketData
import com.crypto.app.tracker.models.marketdata.CoinMarketData
import com.crypto.app.tracker.models.metadata.Description
import com.crypto.app.tracker.models.metadata.Metadata
import com.crypto.app.tracker.models.metadata.Ticker
import com.crypto.app.tracker.service.impl.CoinRestServiceImpl
import spock.lang.Specification

class CoinInfoRestServiceImplSpec extends Specification {
    CoinRestServiceImpl fixture
    def coinDataClient;
    def coinMetaDataClient
    def coinMarketMetaMapper

    def setup(){
        coinDataClient = Mock(CoinMarketDataClient)
        coinMetaDataClient = Mock(CoinMetaDataClient)
        coinMarketMetaMapper = Mock(CoinMarkerMetadataMapper)
        fixture = [coinMarketDataClient: coinDataClient, coinMetaDataClient: coinMetaDataClient, coinMarkerMetadataMapper: coinMarketMetaMapper] as CoinRestServiceImpl
    }
    def "the coinDataClient is called and gets the ticker symbol if a name of a coin is provided and then sends ticker"(){
        given:

        def coinName = "Bitcoin"
        def coinData = [usd: 1.00, usdMarketCap:1.2, usd24hVolume:99.52, usd24hChange:3.637,lastUpdated:171135630] as CoinMarketData
        def description = [englishDescription: "yay"] as Description
        def tickers = [trust_score: "green", tickersBase: "ASEFS"] as Ticker
        def coinMetaData = [
                name       : "bitcoin",
                symbol     : "btc",
                description: description,
                logo       : "https://example.com/bitcoin-logo.png",
                tickers    : [tickers],
        ] as Metadata
        def coinMarketMetaData = [coinMetaData: coinMetaData, coinMarketData: coinData] as CoinMetaMarketData
        def fiatCurrency = "USD"

        when:
        def result = fixture.getCoinDataFromCoinName(coinName, fiatCurrency)

        then:

        1 * coinMetaDataClient.getCoinMetaData(coinName) >> coinMetaData
        1 * coinDataClient.getCoinData("btc", fiatCurrency) >> coinData
        1 * coinMarketMetaMapper.mapToCoinMetaMarketData(coinData, coinMetaData)

    }
    def "the coinDataClient is called and gets the ticker symbol if a name of a coin is provided and then sends ticker"(){
        given:

        def coinTicker = "BTC"
        def coinData = [usd: 1.00, usdMarketCap:1.2, usd24hVolume:99.52, usd24hChange:3.637,lastUpdated:171135630] as CoinMarketData
        def description = [englishDescription: "yay"] as Description
        def tickers = [trust_score: "green", tickersBase: "ASEFS"] as Ticker
        def coinMetaData = [
                name       : "bitcoin",
                symbol     : "BTC",
                description: description,
                logo       : "https://example.com/bitcoin-logo.png",
                tickers    : [tickers],
        ] as Metadata
        def coinMarketMetaData = [coinMetaData: coinMetaData, coinMarketData: coinData] as CoinMetaMarketData
        def fiatCurrency = "USD"


        when:
        def result = fixture.getCoinsByTicker(coinTicker, fiatCurrency)

        then:

        1 * coinDataClient.getCoinData(coinTicker,fiatCurrency) >> coinData
        1 * coinMetaDataClient.getCoinMetaData(coinTicker) >> coinMetaData
        1 * coinMarketMetaMapper.mapToCoinMetaMarketData(coinData, coinMetaData) >> coinMarketMetaData
    }
}
