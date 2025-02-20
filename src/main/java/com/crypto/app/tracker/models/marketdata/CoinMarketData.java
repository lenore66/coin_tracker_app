package com.crypto.app.tracker.models.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CoinMarketData implements Serializable {
    @JsonProperty("FROMSYMBOL")
    private String fromSymbol;
    @JsonProperty("TOSYMBOL")
    private String toSymbol;

    @JsonProperty("FLAGS")
    private String flags;
    @JsonProperty("PRICE")
    private Long price;

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