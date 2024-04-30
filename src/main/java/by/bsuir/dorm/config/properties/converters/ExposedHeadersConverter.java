package by.bsuir.dorm.config.properties.converters;

import by.bsuir.dorm.config.properties.ExposedHeaders;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConfigurationPropertiesBinding
public class ExposedHeadersConverter implements Converter<String, ExposedHeaders> {

    @Override
    public ExposedHeaders convert(@NonNull String source) {
        return new ExposedHeaders(
                source,
                Arrays.stream(source.split(","))
                        .map(String::trim)
                        .toList()
        );
    }
}