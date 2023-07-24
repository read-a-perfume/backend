package io.perfume.api.sample.adapter.utils;

import io.perfume.api.sample.application.port.in.dto.SampleResult;
import io.perfume.api.sample.application.port.utils.mapper.SampleMapperInterface;
import io.perfume.api.sample.domain.Sample;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SampleMapper extends SampleMapperInterface
{
    @Override
    SampleResult toSampleResult(Sample sample);
}
