package org.example.stringCompression;

public class AdvancedStringCompression {

    // First compressor: groups characters and counts repeats
    public static String firstCompressor(String s) {
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < s.length()) {
            int count = 1;
            while (i < s.length() - 1 && s.charAt(i) == s.charAt(i + 1)) {
                count++;
                i++;
            }
            result.append(s.charAt(i));
            if (count > 1) {
                result.append(s.charAt(i)).append(count);
            }
            i++;
        }

        return result.toString();
    }

    // Second compressor: optimizes output from first compressor
    public static String secondCompressor(String s) {
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < s.length()) {
            char ch = s.charAt(i);
            result.append(ch);  // always keep first occurrence
            i++;

            if (i < s.length() && s.charAt(i) == ch) {
                // repeated char with count, skip the duplicate char
                i++; // skip second char
                StringBuilder count = new StringBuilder();
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    count.append(s.charAt(i));
                    i++;
                }
                result.append(count); // add count
            }
        }

        return result.toString();
    }

    // Decompressor: reconstructs original string from second compressor
    public static String decompressor(String s) {
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < s.length()) {
            char ch = s.charAt(i);
            i++;
            StringBuilder countStr = new StringBuilder();

            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                countStr.append(s.charAt(i));
                i++;
            }

            int count = (countStr.length() > 0) ? Integer.parseInt(countStr.toString()) : 1;

            for (int j = 0; j < count; j++) {
                result.append(ch);
            }
        }

        return result.toString();
    }

    // === TEST CASES ===
    public static void main(String[] args) {
        String[] testInputs = {
                "aaaabbcddddd",      // normal
                "aabcc",             // mixed
                "a20b20c1a4",        // long counts
                "abc",               // no repeats
                "a",                 // single character
                "aabbccdd",          // pairs
                "zzzzzzzzzz"         // long repeat of one char
        };

        for (String input : testInputs) {

            System.out.println("Original        : " + input);

            String first = firstCompressor(input);
            System.out.println("First Compress  : " + first);

            String second = secondCompressor(first);
            System.out.println("Second Compress : " + second);

            String decompressed = decompressor(second);
            System.out.println("Decompressed    : " + decompressed);
            System.out.println("-----------------------------");

        }
    }
}
