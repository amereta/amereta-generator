package tech.amereta.generator.util.code.java.util;

import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JavaModifier {

    public static final Map<Predicate<Integer>, String> TYPE_MODIFIERS;
    public static final Map<Predicate<Integer>, String> FIELD_MODIFIERS;
    public static final Map<Predicate<Integer>, String> METHOD_MODIFIERS;

    static {
        Map<Predicate<Integer>, String> typeModifiers = new LinkedHashMap<>();
        typeModifiers.put(Modifier::isPublic, "public");
        typeModifiers.put(Modifier::isProtected, "protected");
        typeModifiers.put(Modifier::isPrivate, "private");
        typeModifiers.put(Modifier::isAbstract, "abstract");
        typeModifiers.put(Modifier::isStatic, "static");
        typeModifiers.put(Modifier::isFinal, "final");
        typeModifiers.put(Modifier::isStrict, "strictfp");
        TYPE_MODIFIERS = typeModifiers;
        Map<Predicate<Integer>, String> fieldModifiers = new LinkedHashMap<>();
        fieldModifiers.put(Modifier::isPublic, "public");
        fieldModifiers.put(Modifier::isProtected, "protected");
        fieldModifiers.put(Modifier::isPrivate, "private");
        fieldModifiers.put(Modifier::isStatic, "static");
        fieldModifiers.put(Modifier::isFinal, "final");
        fieldModifiers.put(Modifier::isTransient, "transient");
        fieldModifiers.put(Modifier::isVolatile, "volatile");
        FIELD_MODIFIERS = fieldModifiers;
        Map<Predicate<Integer>, String> methodModifiers = new LinkedHashMap<>(typeModifiers);
        methodModifiers.put(Modifier::isSynchronized, "synchronized");
        methodModifiers.put(Modifier::isNative, "native");
        METHOD_MODIFIERS = methodModifiers;
    }

    private int modifiers;
    private Map<Predicate<Integer>, String> type;

    public static JavaModifier builder() {
        return new JavaModifier();
    }

    public String render() {
        final String modifiers = type.entrySet().stream()
                .filter((entry) -> entry.getKey().test(this.modifiers)).map(Map.Entry::getValue)
                .collect(Collectors.joining(" "));
        if (!modifiers.isEmpty()) {
            return modifiers + " ";
        }
        return "";
    }

    public int getModifiers() {
        return modifiers;
    }

    public JavaModifier modifiers(int modifiers) {
        setModifiers(modifiers);
        return this;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    public Map<Predicate<Integer>, String> getType() {
        return type;
    }

    public JavaModifier type(Map<Predicate<Integer>, String> type) {
        setType(type);
        return this;
    }

    public void setType(Map<Predicate<Integer>, String> type) {
        this.type = type;
    }
}
