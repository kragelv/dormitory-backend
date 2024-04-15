package by.bsuir.dorm.config.properties;

import java.util.List;

public class AllowedMethods extends StringToListProperty {
    public AllowedMethods(String source, List<String> list) {
        super(source, list);
    }
}
