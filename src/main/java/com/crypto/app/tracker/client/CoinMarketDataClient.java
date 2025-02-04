package com.crypto.app.tracker.client;

import com.crypto.app.tracker.models.marketdata.CoinMarketData;

public interface CoinMarketDataClient {
    CoinMarketData getCoinData(String coinTicker);
}
