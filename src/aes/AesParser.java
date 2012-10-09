/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

/**
 *
 * @author kiro
 */
public class AesParser {

    /**
     * Parse message, transforms and splits it in AES State blocks of 128 bits.
     * Each char is a unicode character of 16-bit, so each block contains 8
     * characters. Adds padding if necessary.
     *
     * @param str
     * @return
     */
    public static State[] getStateBlocks(String str) {
        str = str.trim();
        if (str.length() % 8 != 0) {
            int remainder = (str.length() % 8);
            for (int i = 0; i < (8 - remainder); i++) {
                str += " ";
            }
        }
        int x = str.length();
        System.err.println("x = " + x);
        State[] result = new State[str.length() / 8];
        for (int i = 0; i < str.length() / 8; i++) {
            result[i] = getStateFromText(str.substring(i*8, (i+1)*8));
        }
        return result;
    }

    public static State getStateFromText(String str) {
        State result = new State();
        for (int j = 0; j < 8; j++) {
            int ch = (int) (str.charAt(j));
            result.state[j * 2] = (ch & 0xff00) >> 8;
            result.state[j * 2 + 1] = (ch & 0x00ff);
        }
        return result;
    }

    public static String getStringFromState(State[] states) {
        String result = "";
        for (State state : states) {
            for (int i = 0; i < 8; i++) {
                int ch = (state.state[i * 2] << 8) | state.state[i * 2 + 1];
                result += Character.toString(Character.toChars(ch)[0]);
            }
        }
        return result.trim();
    }
}
