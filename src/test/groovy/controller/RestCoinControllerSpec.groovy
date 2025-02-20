package controller

import com.crypto.app.tracker.controller.RestCoinController
import com.crypto.app.tracker.models.CoinMetaMarketData
import com.crypto.app.tracker.models.marketdata.CoinMarketData
import com.crypto.app.tracker.models.metadata.Description
import com.crypto.app.tracker.models.metadata.Metadata
import com.crypto.app.tracker.models.metadata.Ticker
import com.crypto.app.tracker.service.CoinRestService
import spock.lang.Specification

class RestCoinControllerSpec extends Specification{
    RestCoinController fixture = [] as RestCoinController
    def coinService

    def setup() {
        coinService = Mock(CoinRestService)
        fixture.coinService = coinService
    }

    def "controller is called for calling a coin by the coin name"(){
        given:
        def coinName = "Bitcoin"
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
        def result = fixture.getCoins(coinName,fiatCurrency)
        then:
        1 * coinService.getCoinDataFromCoinName(coinName, fiatCurrency) >> coinMarketMetaData
    }
    def "controller is called for calling a coin by the coin ticker"(){
        given:
        def coinTicker = "BTC".toString()
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
        1 * coinService.getCoinsByTicker(coinTicker ,fiatCurrency) >> coinMarketMetaData
    }
}
