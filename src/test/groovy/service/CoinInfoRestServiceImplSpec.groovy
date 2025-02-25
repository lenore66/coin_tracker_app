package service

import com.crypto.app.tracker.client.CoinMarketDataClient
import com.crypto.app.tracker.client.CoinMetaDataClient
import com.crypto.app.tracker.mapper.CoinMarkertMetadataMapper
import com.crypto.app.tracker.models.CoinMetaMarketData
import com.crypto.app.tracker.models.marketdata.MarketData
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
        coinMarketMetaMapper = Mock(CoinMarkertMetadataMapper)
        fixture = [coinMarketDataClient: coinDataClient, coinMetaDataClient: coinMetaDataClient, coinMarketMetadataMapper: coinMarketMetaMapper] as CoinRestServiceImpl
    }
    def "the coinDataClient is called and gets the ticker symbol if a name of a coin is provided and then sends ticker"(){
        given:

        def coinName = "Bitcoin"
        def coinData =[current_price: 1.00, market_cap:1.2, fully_diluted_valuation:99.52, total_volume:3.637,low_24h:171135630 ] as MarketData
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
        def coinData =[current_price: 1.00, market_cap:1.2, fully_diluted_valuation:99.52, total_volume:3.637,low_24h:171135630 ] as MarketData
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
