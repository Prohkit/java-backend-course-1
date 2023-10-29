package edu.hw3;

import java.util.HashMap;
import java.util.Map;

public class Task1 {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    private Task1() {
    }

    public static String atbash(String stringToEncrypt) {
        Map<Character, Character> alphabetMap = getAlphabetMap();
        char[] encryptedStringChars = stringToEncrypt.toCharArray();
        for (int i = 0; i < encryptedStringChars.length; i++) {
            if (alphabetMap.containsKey(encryptedStringChars[i])) {
                encryptedStringChars[i] = alphabetMap.get(encryptedStringChars[i]);
            }
        }
        return new String(encryptedStringChars);
    }

    private static Map<Character, Character> getAlphabetMap() {
        Map<Character, Character> alphabetMap = new HashMap<>();
        for (int i = 1; i < ALPHABET.length; i++) {
            alphabetMap.put(ALPHABET[i - 1], ALPHABET[ALPHABET.length - i]);
            alphabetMap.put(
                Character.toUpperCase(ALPHABET[i - 1]),
                Character.toUpperCase(ALPHABET[ALPHABET.length - i])
            );
        }
        return alphabetMap;
    }
}
