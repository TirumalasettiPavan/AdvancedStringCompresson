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

    // Optimized Second Compressor
    public static String secondCompressor(String s) {
        StringBuilder letters = new StringBuilder();
        StringBuilder counts = new StringBuilder();
        int i = 0;
        boolean allSame = true;
        String commonCount = null;

        while (i < s.length()) {
            char ch = s.charAt(i);
            letters.append(ch);
            i++;

            if (i < s.length() && s.charAt(i) == ch) {
                i++; // skip duplicate char
                StringBuilder countStr = new StringBuilder();
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    countStr.append(s.charAt(i));
                    i++;
                }

                String thisCount = countStr.toString();
                counts.append(thisCount);

                if (commonCount == null) {
                    commonCount = thisCount;
                } else if (!commonCount.equals(thisCount)) {
                    allSame = false;
                }
            } else {
                counts.append("1");
                if (commonCount == null) {
                    commonCount = "1";
                } else if (!commonCount.equals("1")) {
                    allSame = false;
                }
            }
        }

        // If all counts are same and greater than 1, compress like abcd2
        if (allSame && !commonCount.equals("1")) {
            return letters.toString() + commonCount;
        }

        // Otherwise return like a2b2c1d4
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < letters.length(); j++) {
            result.append(letters.charAt(j));
            if (!counts.substring(j, j + 1).equals("1")) {
                result.append(counts.charAt(j));
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
                "aabbcc",             // mixed
                "a20b20c1a4",        // long counts
                "abc",               // no repeats
                "a",                 // single character
                "aabbccdd",          // pairs → abcd2
                "zzzzzzzzzz",        // long repeat of one char
                "aaabbbccdd"         // uneven → a3b3c2d2
        };

        for (String input : testInputs) {
            System.out.println("Original String        : " + input);

            String first = firstCompressor(input);
            System.out.println("First Compression  : " + first);

            String second = secondCompressor(first);
            System.out.println("Second Compression : " + second);

            String decompressed = decompressor(second);
            System.out.println("Decompression    : " + decompressed);

            System.out.println("--------------------------------------------");
        }
    }
}