package com.crypto.app.tracker.service;

import com.crypto.app.tracker.models.CoinMetaMarketData;

public interface CoinRestService {
    CoinMetaMarketData getCoinDataFromCoinName(String coinTicker, String toCurrency);

    CoinMetaMarketData getCoinsByTicker(String coinTicker, String toCurrency);
}
