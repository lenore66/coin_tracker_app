package com.crypto.app.tracker.service.impl;

import com.crypto.app.tracker.client.CoinMarketDataClient;
import com.crypto.app.tracker.client.CoinMetaDataClient;
import com.crypto.app.tracker.models.CoinMetaMarketData;
import com.crypto.app.tracker.service.CoinRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinRestServiceImpl implements CoinRestService {

    @Autowired
   private CoinMarketDataClient coinMarketDataClient;

    @Autowired
    private CoinMetaDataClient coinMetaDataClient;

    public CoinMetaMarketData getCoinDataFromCoinName(String coinName, String toCurrency){
        CoinMetaMarketData coinMetaMarketData = new CoinMetaMarketData();

        coinMetaMarketData.coinMetaData = coinMetaDataClient.getCoinMetaData(coinName);
        coinMetaMarketData.coinMarketData = coinMarketDataClient.getCoinData( coinMetaMarketData.coinMetaData.coinInfo.metadata.symbol, toCurrency);

        return coinMetaMarketData;
    }
    public CoinMetaMarketData getCoinsByTicker(String coinTicker, String toCurrency){
        CoinMetaMarketData coinMetaMarketData = new CoinMetaMarketData();

        coinMetaMarketData.coinMetaData = coinMetaDataClient.getCoinMetaData(coinTicker);
        coinMetaMarketData.coinMarketData = coinMarketDataClient.getCoinData(coinTicker,toCurrency);

        return coinMetaMarketData;
    }
}
