package com.crypto.app.tracker.models.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class CoinMarketData implements Serializable {
    @JsonProperty("FROMSYMBOL")
    public String fromSymbol;
    @JsonProperty("TOSYMBOL")
    public String toSymbol;

    @JsonProperty("FLAGS")
    public String flags;
    @JsonProperty("PRICE")
    public Long price;

    @JsonProperty("LASTUPDATE")
    public Long lastUpdate;

    @JsonProperty("LASTVOLUME")
    public Long lastVolume;
    @JsonProperty("LASTVOLUMETO")
    public Long lastVolumeTo;


    @JsonProperty("VOLUME24HOUR")
    public Long volume24Hour;

    @JsonProperty("VOLUME24HOURTO")
    public Long volume24HourTo;

    @JsonProperty("CHANGEPCT24HOUR")
    public Long changePercent24Hour;

        }