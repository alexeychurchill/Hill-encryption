package io.github.alexeychurchill.hillencryption.encryptionutils;

/**
 * Alphabet interface
 * Return chars in uppercase
 */

public interface Alphabet {
    boolean contains(char c);
    int position(char c);
    char character(int position);
    int length();
}
