package com.crypto.app.tracker.models;

import com.crypto.app.tracker.models.marketdata.MarketData;
import com.crypto.app.tracker.models.metadata.Metadata;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CoinMetaMarketData implements Serializable {
 private MarketData marketData;
    private Metadata metaData;
}
