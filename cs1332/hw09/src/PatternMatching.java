import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Jamahl Grant
 * @version 1.0
 * @userid jgrant86
 * @GTID 903558339
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     * <p>
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot "
                    + "null or have length of 0 ");
        }

        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text and/or "
                    + "comparator is null.");
        }
        List<Integer> matches = new ArrayList<Integer>();
        int patIndex = 0;
        int textIndex = 0;
        if (text.length() < pattern.length()) {
            return matches;
        }
        int[] failureTable = buildFailureTable(pattern, comparator);
        while (textIndex + (pattern.length() - 1) - patIndex < text.length()) {
            if (comparator.compare(pattern.charAt(patIndex), text.charAt(textIndex)) == 0) {
                if (patIndex == pattern.length() - 1) {
                    matches.add(textIndex - patIndex);
                    patIndex = failureTable[patIndex - 1];
                    patIndex++;
                    textIndex++;
                } else {
                    patIndex++;
                    textIndex++;
                }

            } else if (patIndex == 0) {
                textIndex++;

            } else {
                patIndex = failureTable[patIndex - 1];
            }
        }
        return matches;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     * <p>
     * The table built should be the length of the input pattern.
     * <p>
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     * <p>
     * Ex. pattern = ababac
     * <p>
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     * <p>
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("Pattern and/or "
                    + "comparator cannot be null comparator ");
        }
        if (pattern.length() == 0) {
            int[] failureTable = new int[0];
            return failureTable;
        }
        int[] failureTable = new int[pattern.length()];
        failureTable[0] = 0;
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                failureTable[j] = i + 1;
                i++;
                j++;

            } else if (i == 0) {
                failureTable[j] = 0;
                j++;

            } else {
                i = failureTable[i - 1];
            }
        }
        return failureTable;


    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     * <p>
     * Make sure to implement the buildLastTable() method before implementing
     * this method.
     * <p>
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot "
                    + "null or have length of 0 ");
        }

        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text and/or "
                    + "comparator is null.");
        }
        List<Integer> matches = new ArrayList<Integer>();
        if (text.length() < pattern.length()) {
            return matches;
        }
        HashMap<Character, Integer> lastTable = (HashMap<Character, Integer>) buildLastTable(pattern);
        int textIndex = 0;
        while (textIndex <= text.length() - pattern.length()) {
            int patIndex = pattern.length() - 1;
            while (patIndex >= 0 && comparator.compare(text.charAt(textIndex + patIndex), pattern.charAt(patIndex)) == 0) {
                patIndex--;
            }
            if (patIndex == -1) {
                matches.add(textIndex);
                textIndex++;

            } else {
                if (lastTable.containsKey(text.charAt(textIndex + patIndex))) {
                    int shift = lastTable.get(text.charAt(textIndex + patIndex));
                    if (shift < patIndex) {
                        textIndex = textIndex + patIndex - shift;

                    } else {
                        textIndex++;
                    }

                } else {
                    textIndex = textIndex + patIndex + 1;
                }
            }
        }
        return matches;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * <p>
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     * <p>
     * Ex. pattern = octocat
     * <p>
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     * <p>
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern can't be null");
        }
        HashMap<Character, Integer> lastTable = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            lastTable.put(pattern.charAt(i), i);
        }
        return lastTable;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     * <p>
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     * <p>
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     * <p>
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     * c is the integer value of the current character, and
     * i is the index of the character
     * <p>
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     * <p>
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     * <p>
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     * <p>
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     * <p>
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     * = (142910419 - 98 * 113 ^ 3) * 113 + 121
     * = 170236090
     * <p>
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     * <p>
     * Do NOT use Math.pow() in this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot "
                    + "null or have length of 0 ");
        }

        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text and/or "
                    + "comparator is null.");
        }
        List<Integer> matches = new ArrayList<Integer>();
        if (text.length() < pattern.length()) {
            return matches;
        }
        int base = 1;
        int patternHash = 0;
        int key = 0;
        for (int i = pattern.length() - 1; i >= 0; i--) {
            key += pattern.charAt(i) * base;
            patternHash += text.charAt(i) * base;
            if (i > 0) {
                base *= BASE;
            }
        }
        if (patternHash == key) {
            boolean match = true;
            for (int i = 0; i < pattern.length(); i++) {
                if (comparator.compare(pattern.charAt(i), text.charAt(i)) != 0) {
                    match = false;
                    break;
                }
            }
            if (match) {
                matches.add(0);
            }
        }
        for (int i = 1; i <= text.length() - pattern.length(); i++) {
            patternHash = patternHash - (text.charAt(i - 1) * base);
            patternHash = (patternHash * BASE) + text.charAt(i + pattern.length() - 1);
            if (patternHash == key) {
                boolean match = true;
                int patIndex = 0;
                for (int j = i; j <= i + pattern.length() - 1; j++) {
                    if (comparator.compare(pattern.charAt(patIndex), text.charAt(j)) != 0) {
                        match = false;
                        break;
                    }
                    patIndex++;
                }
                if (match) {
                    matches.add(i);
                }
            }
        }
        return matches;
    }
}
