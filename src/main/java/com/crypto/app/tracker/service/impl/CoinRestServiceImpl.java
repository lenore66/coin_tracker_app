package com.crypto.app.tracker.service.impl;

import com.crypto.app.tracker.client.CoinMarketDataClient;
import com.crypto.app.tracker.client.CoinMetaDataClient;
import com.crypto.app.tracker.mapper.CoinMarkertMetadataMapper;
import com.crypto.app.tracker.models.CoinMetaMarketData;
import com.crypto.app.tracker.models.marketdata.MarketData;
import com.crypto.app.tracker.models.metadata.Metadata;
import com.crypto.app.tracker.service.CoinRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.error.Mark;

@Service
public class CoinRestServiceImpl implements CoinRestService {

    @Autowired
   private CoinMarketDataClient coinMarketDataClient;

    @Autowired
    private CoinMetaDataClient coinMetaDataClient;

    @Autowired
    private CoinMarkertMetadataMapper coinMarketMetadataMapper;
    public CoinMetaMarketData getCoinDataFromCoinName(String coinName, String toCurrency){
        CoinMetaMarketData coinMetaMarketData = new CoinMetaMarketData();

        Metadata coinMetadata = coinMetaDataClient.getCoinMetaData(coinName);
       MarketData coinMarketData = coinMarketDataClient.getCoinData(coinMetadata.getId(), toCurrency);
        System.out.println(coinMetaMarketData);

        coinMetaMarketData = coinMarketMetadataMapper.mapToCoinMetaMarketData(coinMarketData,coinMetadata);

        return coinMetaMarketData;
    }
    public CoinMetaMarketData getCoinsByTicker(String coinTicker, String toCurrency){
        Metadata coinMetadata = (coinMetaDataClient.getCoinMetaData(coinTicker));

        MarketData  coinMarketData = coinMarketDataClient.getCoinData(coinMetadata.getId(),toCurrency);

        CoinMetaMarketData coinMetaMarketData = coinMarketMetadataMapper.mapToCoinMetaMarketData(coinMarketData,coinMetadata);
        return coinMetaMarketData;
    }
}
