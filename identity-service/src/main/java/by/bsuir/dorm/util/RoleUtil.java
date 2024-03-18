package by.bsuir.dorm.util;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class RoleUtil {
    private RoleUtil() { }

    public static final String ROLE_PREFIX = "";
    public static SimpleGrantedAuthority mapRoleName(String name) {
        return new SimpleGrantedAuthority(prefixRoleName(name));
    }

    public static String prefixRoleName(String name) {
        return ROLE_PREFIX + name;
    }

    public static boolean isPrefixedRole(String name) {
        return name.toUpperCase().startsWith(ROLE_PREFIX);
    }
}
