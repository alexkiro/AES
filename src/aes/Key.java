/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

/**
 *
 * @author kiro
 */
public class Key {

    private WordPoly[] keyExpansion;
    private int Nr;
    private int Nk;
    private final int Nb = 4;

    /**
     * Expand the cipher key from a String. <br />
     * 
     * 8 char means 128 bit key <br />
     * 12 char means 192 bit key <br />
     * 16 char means 256 bit key <br />
     * @param cipherKey 
     */
    public Key(String cipherKey) {
        int n = cipherKey.length();
        //check if valid for 128 , 192 or 256 bit
        if ((n == 8) || (n == 12) || (n == 16)) {
            setRoundNumber(n);
            keyExpansion = new WordPoly[Nb * (Nr + 1)];
            for (int i = 0; i < Nk; i++) {
                WordPoly word = AesParser.charsToWord(
                        cipherKey.charAt(i * 2), cipherKey.charAt(i * 2 + 1));
                keyExpansion[i] = word;
            }
            for (int i = Nk; i < Nb * (Nr + 1); i++) {
                WordPoly temp = keyExpansion[i - 1];
                if (i % Nk == 0) {
                    temp = subWord(rotWord(temp)).addTo(RCon.getInstance().rcon.get(i / Nk - 1));
                }
                keyExpansion[i] = temp.addTo(keyExpansion[i - Nk]);
            }
        } else {
            //TODO: throw exception here
        }
    }
    
    public int getNr(){
        return Nr;
    }

    /**
     * Get the key for specific round. 
     * Round 0 is the original cipher key.
     * @param round
     * @return 
     */
    public State getRoundKey(int round) {
        State result = new State();
        for (int j = 0; j < 4; j++) {
            result.wordToCollumn(keyExpansion[round * 4 + j], j);
        }
        return result;
    }
    
    private WordPoly rotWord(WordPoly word) {
        return word.multiply(new WordPoly(1, 0, 0, 0));
    }

    private WordPoly subWord(WordPoly word) {
        WordPoly result = new WordPoly();
        int x = (word.x0.poly & 0xf0) >> 4;
        int y = (word.x0.poly & 0x0f);
        result.x0.poly = SBox.getInstance().apply(x, y);
        x = (word.x1.poly & 0xf0) >> 4;
        y = (word.x1.poly & 0x0f);
        result.x1.poly = SBox.getInstance().apply(x, y);
        x = (word.x2.poly & 0xf0) >> 4;
        y = (word.x2.poly & 0x0f);
        result.x2.poly = SBox.getInstance().apply(x, y);
        x = (word.x3.poly & 0xf0) >> 4;
        y = (word.x3.poly & 0x0f);
        result.x3.poly = SBox.getInstance().apply(x, y);
        return result;
    }

    private void setRoundNumber(int cipherKeyLenght) {
        Nk = cipherKeyLenght / 2;
        switch (cipherKeyLenght) {
            case 8:
                Nr = 10;
                break;
            case 12:
                Nr = 12;
                break;
            case 16:
                Nr = 14;
                break;
        }
    }
}
