package com.crypto.app.tracker.client;

import com.crypto.app.tracker.models.marketdata.MarketData;

public interface CoinMarketDataClient {
    MarketData getCoinData(String coinTicker, String toCurrency);
}
