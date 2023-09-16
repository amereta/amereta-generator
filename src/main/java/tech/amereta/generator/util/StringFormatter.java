package tech.amereta.generator.util;

import java.util.stream.Collectors;

public class StringFormatter {

    public static String toPascalCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String toSnakeCase(String string) {
        return string.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    public static String camelToKebab(String string) {
        return string.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
    }
}
