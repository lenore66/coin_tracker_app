package com.crypto.app.tracker.client;

import com.crypto.app.tracker.models.metadata.Metadata;

public interface CoinMetaDataClient {
    Metadata getCoinMetaData(String coinName);
}
