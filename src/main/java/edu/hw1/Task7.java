package edu.hw1;

public class Task7 {

    private Task7() {
    }

    private static byte[] getBinaryArray(int n) {
        String binaryString = Integer.toBinaryString(n);
        char[] binaryChars = binaryString.toCharArray();
        byte[] binaryArray = new byte[binaryChars.length];

        for (int i = 0; i < binaryChars.length; i++) {
            binaryArray[i] = Byte.parseByte(String.valueOf(binaryChars[i]));
        }
        return binaryArray;
    }

    private static int binaryArrayToInt(byte[] binaryArray) {
        int result = 0;
        for (int i = binaryArray.length - 1; i >= 0; i--) {
            if (binaryArray[i] == 1) {
                result = result + (int) Math.pow(2, binaryArray.length - 1 - i);
            }
        }
        return result;
    }

    public static int rotateRight(int n, int shift) {
        byte[] binaryArray = getBinaryArray(n);
        boolean isEndBit = false;
        for (int i = 1; i <= shift; i++) {
            if (binaryArray[binaryArray.length - 1] == 1) {
                isEndBit = true;
            }

            for (int j = binaryArray.length - 1; j > 0; j--) {
                binaryArray[j] = binaryArray[j - 1];
            }

            if (isEndBit) {
                binaryArray[0] = 1;
                isEndBit = false;
            } else {
                binaryArray[0] = 0;
            }
        }
        return binaryArrayToInt(binaryArray);
    }

    public static int rotateLeft(int n, int shift) {
        byte[] binaryArray = getBinaryArray(n);
        boolean isEndBit = false;
        for (int i = 1; i <= shift; i++) {
            if (binaryArray[0] == 1) {
                isEndBit = true;
            }

            for (int j = 0; j < binaryArray.length - 1; j++) {
                binaryArray[j] = binaryArray[j + 1];
            }

            if (isEndBit) {
                binaryArray[binaryArray.length - 1] = 1;
                isEndBit = false;
            } else {
                binaryArray[binaryArray.length - 1] = 0;
            }
        }
        return binaryArrayToInt(binaryArray);
    }
}
