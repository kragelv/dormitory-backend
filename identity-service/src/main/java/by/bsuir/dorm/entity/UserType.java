package by.bsuir.dorm.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public enum UserType {
    Student(0),
    Employee(1);

    @Getter
    private final int intValue;

    private static final Map<Integer, UserType> valuesMap;

    static {
        valuesMap = new HashMap<>();
        for (UserType userType: values()) {
            valuesMap.put(userType.getIntValue(), userType);
        }
    }

    public static Optional<UserType> fromIntValue(int intValue){
        return Optional.ofNullable(valuesMap.get(intValue));
    }
}
