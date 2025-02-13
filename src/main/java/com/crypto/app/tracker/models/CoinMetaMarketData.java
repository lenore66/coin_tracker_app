package com.crypto.app.tracker.models;

import com.crypto.app.tracker.models.marketdata.CoinMarketData;
import com.crypto.app.tracker.models.metadata.CoinMetaData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CoinMetaMarketData {
    public CoinMarketData coinMarketData;
    public CoinMetaData coinMetaData;
}
