package com.crypto.app.tracker.mapper;

import com.crypto.app.tracker.models.CoinMetaMarketData;
import com.crypto.app.tracker.models.marketdata.CoinMarketData;
import com.crypto.app.tracker.models.metadata.Metadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CoinMarkerMetadataMapper {

    CoinMarkerMetadataMapper INSTANCE = Mappers.getMapper( CoinMarkerMetadataMapper.class );
@Mapping(target = "coinMarketData", source = "coinMarketData")
@Mapping(target = "coinMetaData", source = "coinMetaData")
    CoinMetaMarketData mapToCoinMetaMarketData(CoinMarketData coinMarketData, Metadata coinMetaData);
}
