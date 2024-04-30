package by.bsuir.dorm.config.properties;

import java.util.List;

public class ExposedHeaders extends StringToListProperty {
    public ExposedHeaders(String source, List<String> list) {
        super(source, list);
    }
}
