package com.crypto.app.tracker.service.impl;

import com.crypto.app.tracker.models.CoinData;
import com.crypto.app.tracker.service.CoinRestService;
import org.springframework.stereotype.Service;

@Service
public class CoinRestServiceImpl implements CoinRestService {
    public CoinData getCoinData(){
       CoinData coinData = new CoinData();
        return coinData;
    }
}
