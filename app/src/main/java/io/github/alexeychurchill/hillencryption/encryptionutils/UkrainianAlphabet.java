package io.github.alexeychurchill.hillencryption.encryptionutils;

/**
 * Ukrainian alphabet class
 */

public class UkrainianAlphabet implements Alphabet {
    private static final String ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ,.;?_-~ !1234567890:";

    @Override
    public boolean contains(char c) {
        return ALPHABET.contains(String.valueOf(Character.toUpperCase(c)));
    }

    @Override
    public int position(char c) {
        return ALPHABET.indexOf(Character.toUpperCase(c));
    }

    @Override
    public char character(int position) {
        if (position < 0) {
            position = 0;
        }
        if (position >= length()) {
            position = length() - 1;
        }
        return ALPHABET.charAt(position);
    }

    @Override
    public int length() {
        return ALPHABET.length();
    }
}
