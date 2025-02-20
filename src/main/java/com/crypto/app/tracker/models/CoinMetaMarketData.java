package com.crypto.app.tracker.models;

import com.crypto.app.tracker.models.marketdata.CoinMarketData;
import com.crypto.app.tracker.models.metadata.Metadata;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Jacksonized
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CoinMetaMarketData implements Serializable {
    private CoinMarketData coinMarketData;
    private Metadata coinMetaData;
}
