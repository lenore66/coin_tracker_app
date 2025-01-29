package com.crypto.app.tracker.client;

import com.crypto.app.tracker.models.CoinData;

public interface CoinDataClient {
    CoinData getCoinData(String coinTicker);
}
