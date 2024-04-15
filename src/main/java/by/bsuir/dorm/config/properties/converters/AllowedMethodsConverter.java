package by.bsuir.dorm.config.properties.converters;

import by.bsuir.dorm.config.properties.AllowedMethods;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConfigurationPropertiesBinding
public class AllowedMethodsConverter implements Converter<String, AllowedMethods> {

    @Override
    public AllowedMethods convert(@NonNull String source) {
        return new AllowedMethods(
                source,
                Arrays.stream(source.split(","))
                        .map(String::trim)
                        .toList()
        );
    }
}
