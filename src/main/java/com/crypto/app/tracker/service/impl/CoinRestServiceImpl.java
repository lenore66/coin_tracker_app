package com.crypto.app.tracker.service.impl;

import com.crypto.app.tracker.client.CoinDataClient;
import com.crypto.app.tracker.models.CoinData;
import com.crypto.app.tracker.service.CoinRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinRestServiceImpl implements CoinRestService {

    @Autowired
   private CoinDataClient coinDataClient;

    public CoinData getCoinData(String coinTicker){
        return coinDataClient.getCoinData(coinTicker);
    }
}
