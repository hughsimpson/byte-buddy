package com.blogspot.mydailyjava.bytebuddy;

import java.util.Collection;
import java.util.Random;

public interface NamingStrategy {

    static interface UnnamedType {

        Class<?> getSuperClass();

        Collection<Class<?>> getDeclaredInterfaces();

        Visibility getVisibility();

        TypeManifestation getTypeManifestation();

        SyntheticState getSyntheticState();

        int getClassFormatVersion();
    }

    static class PrefixingRandom implements NamingStrategy {

        private static final String JAVA_LANG_PACKAGE = "java.lang.";
        private static final String BYTE_BUDDY_RENAME_PACKAGE = "com.blogspot.mydailyjava.bytebuddy.renamed";

        private final String prefix;
        private final String javaLangPackagePrefix;
        private final Random random;

        public PrefixingRandom(String prefix) {
            this(prefix, BYTE_BUDDY_RENAME_PACKAGE);
        }

        public PrefixingRandom(String prefix, String javaLangPackagePrefix) {
            this.prefix = prefix;
            this.javaLangPackagePrefix = javaLangPackagePrefix;
            this.random = new Random();
        }

        @Override
        public String getName(UnnamedType unnamedType) {
            String superClassName = unnamedType.getSuperClass().getName();
            if (superClassName.startsWith(JAVA_LANG_PACKAGE)) {
                superClassName = javaLangPackagePrefix + "." + superClassName;
            }
            return String.format("%s$$%s$$%d", superClassName, prefix, Math.abs(random.nextInt()));
        }
    }

    static class Fixed implements NamingStrategy {

        private final String name;

        public Fixed(String name) {
            this.name = name;
        }

        @Override
        public String getName(UnnamedType UnnamedType) {
            return name;
        }
    }

    String getName(UnnamedType unnamedType);
}
