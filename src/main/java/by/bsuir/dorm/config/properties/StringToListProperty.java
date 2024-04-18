package by.bsuir.dorm.config.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class StringToListProperty {
    private String source;
    private List<String> list;

    public StringToListProperty(String source, List<String> list) {
        this.source = source;
        this.list = list;
    }
}
