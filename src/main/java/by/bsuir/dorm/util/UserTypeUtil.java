package by.bsuir.dorm.util;

public class UserTypeUtil {
    private UserTypeUtil() { }

    public static final String USER_TYPE_PREFIX = "TYPE_";

    public static String prefixUserTypeName(String name) {
        if (isPrefixedUserTypeName(name))
            return name;
        return USER_TYPE_PREFIX + name;
    }

    public static boolean isPrefixedUserTypeName(String name) {
        return name.startsWith(USER_TYPE_PREFIX);
    }
}
