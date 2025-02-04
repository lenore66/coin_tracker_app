package com.crypto.app.tracker.service;

import com.crypto.app.tracker.models.CoinMetaMarketData;
import com.crypto.app.tracker.models.marketdata.CoinMarketData;

public interface CoinRestService {
    CoinMetaMarketData getCoinDataFromCoinName(String coinTicker);
}
