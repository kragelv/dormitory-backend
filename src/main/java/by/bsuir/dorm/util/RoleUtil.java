package by.bsuir.dorm.util;

public class RoleUtil {
    private RoleUtil() { }

    public static final String ROLE_PREFIX = "ROLE_";

    public static String prefixRoleName(String name) {
        if (isPrefixedRole(name))
            return name;
        return ROLE_PREFIX + name;
    }

    public static boolean isPrefixedRole(String name) {
        return name.startsWith(ROLE_PREFIX);
    }
}
