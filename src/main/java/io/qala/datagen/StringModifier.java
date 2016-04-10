package io.qala.datagen;

import io.qala.datagen.adaptors.CommonsLang3RandomStringUtils;

import static io.qala.datagen.RandomValue.upTo;

public interface StringModifier {
    String modify(String original);

    class Impls {
        static StringModifier spaces() {
            return multipleOf(' ');
        }

        static StringModifier specialSymbol() {
            return oneOf(Vocabulary.specialSymbols());
        }

        static StringModifier oneOf(String chars) {
            return oneOf(chars.toCharArray());
        }

        static StringModifier oneOf(final char... chars) {
            return new StringModifier() {
                @Override public String modify(String original) {
                    int index = upTo(original.length()).integer();
                    String symbol = CommonsLang3RandomStringUtils.random(1, chars);
                    return new StringBuilder(original).replace(index, index + 1, symbol).toString();
                }
            };
        }

        static StringModifier multipleOf(String chars) {
            return multipleOf(chars.toCharArray());
        }

        static StringModifier multipleOf(final char... chars) {
            return new StringModifier() {
                @Override public String modify(String original) {
                    int nOfSymbols = upTo(original.length()).integer();
                    StringBuilder stringBuilder = new StringBuilder(original);
                    for (int i = 0; i < nOfSymbols; i++) {
                        int index = upTo(original.length()).integer();
                        String symbol = CommonsLang3RandomStringUtils.random(1, chars);
                        stringBuilder.replace(index, index + 1, symbol);
                    }
                    return stringBuilder.toString();
                }
            };
        }
    }
}
