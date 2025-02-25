package com.crypto.app.tracker.mapper;

import com.crypto.app.tracker.models.CoinMetaMarketData;
import com.crypto.app.tracker.models.marketdata.MarketData;
import com.crypto.app.tracker.models.metadata.Metadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CoinMarkertMetadataMapper {

    CoinMarkertMetadataMapper INSTANCE = Mappers.getMapper( CoinMarkertMetadataMapper.class );
@Mapping(target = "marketData", source = "coinMarketData")
@Mapping(target = "metaData", source = "coinMetaData")
    CoinMetaMarketData mapToCoinMetaMarketData(MarketData coinMarketData, Metadata coinMetaData);
}
