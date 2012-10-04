/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author kiro
 */
public class Aes extends Application {

    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Aes");
        primaryStage.setScene(scene);
        //primaryStage.show();
        int[] ss = {0xD4, 0xE0, 0xb8, 0x1e,
            0xbf, 0xb4, 0x41, 0x27,
            0x5d, 0x52, 0x11, 0x98,
            0x30, 0xae, 0xf1, 0xe5};
        int[] kk = { 0xa0, 0x88, 0x23, 0x2a,
            0xfa,0x54,0xa3,0x6c,
            0xfe,0x2c,0x39,0x76,
            0x17,0xb1,0x39,0x05};
        
        State s = new State(kk);
        System.err.println(s);
//        mixColumns(s);
//        System.err.println(s);
//        RCon.getInstance();
        State roundKey = getRoundKey(s, 2);
        System.err.println("roundKey = " + roundKey);

    }
    
    public State getRoundKey(State prevKey, int round){
        State roundKey = new State();
        WPoly word = prevKey.collumnAsWord(3);
        word = rotWord(word);
        subBytes(word);
        roundKey.wordToCollumn(applyRCon(prevKey.collumnAsWord(0), word, round),0);
        for (int i = 1; i < 4; i++) {
            roundKey.wordToCollumn(prevKey.collumnAsWord(i).addTo(roundKey.collumnAsWord(i-1)), i);
        }
        return roundKey;
        
    }
    
    public WPoly applyRCon(WPoly w1, WPoly w2, int round){
        return w1.addTo(w2).addTo(RCon.getInstance().rcon.get(round));
    }
    
    public WPoly rotWord(WPoly word){
        return word.multiply(new WPoly(1, 0, 0, 0));
    }

    public void addRoundKey(State s, State key) {
        for (int i = 0; i < 4; i++) {
            WPoly word = s.collumnAsWord(i);
            WPoly other = key.collumnAsWord(i);
            WPoly roundWord = word.addTo(other); 
            s.wordToCollumn(roundWord, i);
        }
    }

    public void mixColumns(State s) {
        WPoly other = new WPoly(3, 1, 1, 2); //fixed polynomial
        for (int i = 0; i < 4; i++) {
            WPoly word = s.collumnAsWord(i);
            WPoly mixedWord = word.multiply(other); //multiply modulo x^4 + 1
            s.wordToCollumn(mixedWord, i);
        }
    }

    public void shiftRows(State s) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i; j++) {
                int rowHead = s.state[i * 4];
                for (int k = 0; k < 3; k++) {
                    s.state[i * 4 + k] = s.state[i * 4 + k + 1];
                }
                s.state[i * 4 + 3] = rowHead;
            }
        }
    }

    public void subBytes(State s) {
        for (int i = 0; i < 16; i++) {
            int si = s.state[i];
            int x = (si & 0xf0) >> 4;
            int y = (si & 0x0f);
            s.state[i] = SBox.getInstance().apply(x, y);
        }
    }
    
    public void subBytes(WPoly word) {
            int x = (word.x0.poly & 0xf0) >> 4;
            int y = (word.x0.poly & 0x0f);
            word.x0.poly = SBox.getInstance().apply(x, y);
            x = (word.x1.poly & 0xf0) >> 4;
            y = (word.x1.poly & 0x0f);
            word.x1.poly = SBox.getInstance().apply(x, y);
            x = (word.x2.poly & 0xf0) >> 4;
            y = (word.x2.poly & 0x0f);
            word.x2.poly = SBox.getInstance().apply(x, y);
            x = (word.x3.poly & 0xf0) >> 4;
            y = (word.x3.poly & 0x0f);
            word.x3.poly = SBox.getInstance().apply(x, y);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
