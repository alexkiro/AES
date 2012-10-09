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
        if (str.length() % 8 != 0) {
            for (int i = 0; i < (8 - (str.length() % 8)); i++) {
                str += " ";
            }
        }
        State[] result = new State[str.length() / 8];
        for (int i = 0; i < str.length() / 8; i++) {
            result[i] = new State();
            for (int j = 0; j < 8; j++) {
                int ch = (int)(str.charAt(i * 8 + j));
                result[i].state[j * 2] = (ch & 0xff00) >> 8;
                result[i].state[j * 2 + 1] = (ch & 0x00ff);
            }
        }
        return result;
    }
    
    public static String getStringFromState(State [] states){
        String result = "";
        for (State state : states) {
            for (int i = 0; i < 8; i++) {
                int ch = (state.state[i * 2] << 8) | state.state[i * 2 + 1];                
                result += Character.toString(Character.toChars(ch)[0]);
            }
        }        
        return result;
    }
}
