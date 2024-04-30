package by.bsuir.dorm.config.properties.converters;

import by.bsuir.dorm.config.properties.AllowedOrigins;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConfigurationPropertiesBinding
public class AllowedOriginsConverter implements Converter<String, AllowedOrigins> {

    @Override
    public AllowedOrigins convert(@NonNull String source) {
        return new AllowedOrigins(
                source,
                Arrays.stream(source.split(","))
                        .map(String::trim)
                        .toList()
        );
    }
}
