package org.example.stringCompression;

public class AdvancedStringCompression {

    // Compressor 1: Run-Length Encoding
    public static String runLengthEncode(String input) {
        if (input.isEmpty()) return "";

        StringBuilder compressed = new StringBuilder();
        int count = 1;

        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == input.charAt(i - 1)) {
                count++;
            } else {
                compressed.append(input.charAt(i - 1)).append(count);
                count = 1;
            }
        }
        compressed.append(input.charAt(input.length() - 1)).append(count);
        return compressed.toString();
    }

    public static String optimizeCompression(String input) {
        if (input.isEmpty()) return "";

        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < input.length()) {
            char currentChar = input.charAt(i);
            i++;
            StringBuilder numStr = new StringBuilder();

            while (i < input.length() && Character.isDigit(input.charAt(i))) {
                numStr.append(input.charAt(i));
                i++;
            }

            int currentCount = numStr.length() == 0 ? 1 : Integer.parseInt(numStr.toString());

            result.append(currentChar);

            // Check for consecutive chars with same count
            int j = i;
            StringBuilder nextBlock = new StringBuilder();
            while (j < input.length()) {
                char nextChar = input.charAt(j);
                j++;
                StringBuilder nextNumStr = new StringBuilder();
                while (j < input.length() && Character.isDigit(input.charAt(j))) {
                    nextNumStr.append(input.charAt(j));
                    j++;
                }
                int nextCount = nextNumStr.length() == 0 ? 1 : Integer.parseInt(nextNumStr.toString());

                if (nextCount == currentCount) {
                    result.append(nextChar);
                    i = j;
                } else {
                    break;
                }
            }

            // Now decide whether to append count
            if (currentCount > 1) result.append(currentCount);
        }

        return result.toString();
    }


    // Decompressor: From compressed string to original string
    public static String decompress(String input) {
        if (input.isEmpty()) return "";

        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < input.length()) {
            char currentChar = input.charAt(i);
            i++;
            StringBuilder numStr = new StringBuilder();

            while (i < input.length() && Character.isDigit(input.charAt(i))) {
                numStr.append(input.charAt(i));
                i++;
            }

            int count = (numStr.length() == 0) ? 1 : Integer.parseInt(numStr.toString());
            result.append(String.valueOf(currentChar).repeat(count));
        }

        return result.toString();
    }

    // Test Cases
    public static void main(String[] args) {
        String[] testCases = {
                "aabcccccaaa",
                "aasdcv",
                "aabbasccv",
                "abc",
                "",
                "zzzz"
        };

        for (String test : testCases) {
            System.out.println("Original: " + test);
            String compressed = runLengthEncode(test);
            System.out.println("Compressed (RLE): " + compressed);
            String optimized = optimizeCompression(compressed);
            System.out.println("Optimized: " + optimized);
            String decompressed = decompress(optimized);
            System.out.println("Decompressed: " + decompressed);
            System.out.println("-----------------------------");
        }
    }
}