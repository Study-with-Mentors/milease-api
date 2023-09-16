package vn.id.milease.mileaseapi.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {
    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFullTypeMatchingRequired(true)
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper;
    }
}
