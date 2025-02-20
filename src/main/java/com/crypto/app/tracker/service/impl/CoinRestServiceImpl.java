package com.crypto.app.tracker.service.impl;

import com.crypto.app.tracker.client.CoinMarketDataClient;
import com.crypto.app.tracker.client.CoinMetaDataClient;
import com.crypto.app.tracker.mapper.CoinMarkerMetadataMapper;
import com.crypto.app.tracker.models.CoinMetaMarketData;
import com.crypto.app.tracker.models.marketdata.CoinMarketData;
import com.crypto.app.tracker.models.metadata.Metadata;
import com.crypto.app.tracker.service.CoinRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinRestServiceImpl implements CoinRestService {

    @Autowired
   private CoinMarketDataClient coinMarketDataClient;

    @Autowired
    private CoinMetaDataClient coinMetaDataClient;

    @Autowired
    private CoinMarkerMetadataMapper coinMarkerMetadataMapper;
    public CoinMetaMarketData getCoinDataFromCoinName(String coinName, String toCurrency){
        CoinMetaMarketData coinMetaMarketData = new CoinMetaMarketData();

        Metadata coinMetadata = coinMetaDataClient.getCoinMetaData(coinName);
       CoinMarketData coinMarketData = coinMarketDataClient.getCoinData(coinMetadata.getSymbol(), toCurrency);
        System.out.println(coinMetaMarketData);

        coinMetaMarketData = coinMarkerMetadataMapper.mapToCoinMetaMarketData(coinMarketData,coinMetadata);

        return coinMetaMarketData;
    }
    public CoinMetaMarketData getCoinsByTicker(String coinTicker, String toCurrency){

        Metadata coinMetadata = (coinMetaDataClient.getCoinMetaData(coinTicker));
       CoinMarketData  coinMarketData = coinMarketDataClient.getCoinData(coinTicker,toCurrency);

        CoinMetaMarketData coinMetaMarketData = coinMarkerMetadataMapper.mapToCoinMetaMarketData(coinMarketData,coinMetadata);
        return coinMetaMarketData;
    }
}
