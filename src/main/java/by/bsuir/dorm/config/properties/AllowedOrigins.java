package by.bsuir.dorm.config.properties;

import java.util.List;

public class AllowedOrigins extends StringToListProperty {
    public AllowedOrigins(String source, List<String> list) {
        super(source, list);
    }
}
