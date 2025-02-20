package com.crypto.app.tracker.models.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ticker {

    @JsonProperty ( "trust_score")
    private String trust_score;
    @JsonProperty("base")
    private String tickersBase;

}
