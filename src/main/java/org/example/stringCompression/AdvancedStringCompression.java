package org.example.stringCompression;

public class AdvancedStringCompression {

    // First compression method is to compress the string in basic level
    public static String BasicCompression(String s) {
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

    // Second compression method is to compress the string in advanced level
    public static String advancedCompression(String s) {
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

        // counts are same it will concat and return count if count > 1 for example a2b2 it will concat ab2
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

    // Decompressor: it will decode the compression string to normal form of the string
    public static String decompression(String s) {
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

    // calling basic & advanced ,decompression method
    public static void main(String[] args) {
        // string array to text all variety of inputs
        String[] testInputs = {
                "aaaabbcddddd",
                "aabbcc",
                "a20b20c1a4",
                "abc",
                "a",
                "aabbccdd",
                "zzzzzzzzzz",
                "aaabbbccdd"
        };

        for (String input : testInputs) {
            System.out.println("Original String        : " + input);

            String first = BasicCompression(input);
            System.out.println("Basic compression form of string  : " + first);

            String second = advancedCompression(first);
            System.out.println("Advanced compression form of string : " + second);

            String decompressed = decompression(second);
            System.out.println("decompression of compressed string into normal form     : " + decompressed);

            System.out.println("--------------------------------------------");
        }
    }
}