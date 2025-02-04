package com.crypto.app.tracker.client;

import com.crypto.app.tracker.models.metadata.CoinMetaData;

public interface CoinMetaDataClient {
    CoinMetaData getCoinMetaData(String coinName);
}
