package com.crypto.app.tracker.service.impl;

import com.crypto.app.tracker.client.CoinMarketDataClient;
import com.crypto.app.tracker.client.CoinMetaDataClient;
import com.crypto.app.tracker.models.CoinMetaMarketData;
import com.crypto.app.tracker.models.marketdata.CoinMarketData;
import com.crypto.app.tracker.models.metadata.CoinMetaData;
import com.crypto.app.tracker.service.CoinRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinRestServiceImpl implements CoinRestService {

    @Autowired
   private CoinMarketDataClient coinMarketDataClient;

    @Autowired
    private CoinMetaDataClient coinMetaDataClient;

    public CoinMetaMarketData getCoinDataFromCoinName(String coinName){
        CoinMetaMarketData coinMetaMarketData = new CoinMetaMarketData();

        coinMetaMarketData.coinMetaData = coinMetaDataClient.getCoinMetaData(coinName);
        coinMetaMarketData.coinMarketData = coinMarketDataClient.getCoinData( coinMetaMarketData.coinMetaData.coinInfo.metadata.symbol);

        return coinMetaMarketData;
    }
}
