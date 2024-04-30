package by.bsuir.dorm.config.properties;

import java.util.List;

public class AllowedHeaders extends StringToListProperty {
    public AllowedHeaders(String source, List<String> list) {
        super(source, list);
    }
}
