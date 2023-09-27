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

    public static String toHumaneCase(String string) {
        return String.join(" ", findWordsInMixedCase(string));
    }

    public static String toSnakeCase(String string) {
        return string.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
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
