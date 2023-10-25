package tech.amereta.generator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringFormatter {

    private static final Pattern WORD_FINDER = Pattern.compile("(([A-Z]?[a-z]+)|([A-Z]))");

    public static String toPascalCase(String string) {
        return findWordsInMixedCase(string)
                .stream()
                .map(item -> item.substring(0, 1).toUpperCase() + item.substring(1).toLowerCase())
                .collect(Collectors.joining());
    }

    public static String toCamelCase(String string) {
        final List<String> words = findWordsInMixedCase(string);
        return words.get(0).toLowerCase() +
                words.subList(1, words.size())
                        .stream()
                        .map(item -> item.substring(0, 1).toUpperCase() + item.substring(1).toLowerCase())
                        .collect(Collectors.joining());
    }

    public static String toHumaneCase(String string) {
        return String.join(" ", findWordsInMixedCase(string));
    }

    public static String toSnakeCase(String string) {
        return string.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    public static String toKebabCase(String string) {
        return string.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
    }

    public static String toPlural(String string) {
        if (string.charAt(string.length() - 1) == 'y') {
            return string.substring(0, string.length() - 1) + "ies";
        } else if (string.charAt(string.length() - 1) == 's') {
            return string + "es";
        }
        return string + "s";
    }

    private static List<String> findWordsInMixedCase(String text) {
        Matcher matcher = WORD_FINDER.matcher(text);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words;
    }
}
