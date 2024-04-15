package by.bsuir.dorm.config.properties.converters;

import by.bsuir.dorm.config.properties.AllowedHeaders;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConfigurationPropertiesBinding
public class AllowedHeadersConverter implements Converter<String, AllowedHeaders> {

    @Override
    public AllowedHeaders convert(@NonNull String source) {
        return new AllowedHeaders(
                source,
                Arrays.stream(source.split(","))
                        .map(String::trim)
                        .toList()
        );
    }
}
