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
        int [] ss = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        State s = new State(ss);
        System.err.println("s = " + s);
        shiftRows(s);
        System.err.println("s = " + s);        
        
    }
    
    public void mixColumns(State s){
        WPoly other = new WPoly(3, 1, 1, 2); //fixed polynomial
        for (int i = 0; i < 4; i++) {
            WPoly word = s.collumnAsWord(i);
            WPoly mixedWord = word.multiply(other); //multiply modulo x^4 + 1
            s.wordAsCollumn(word, i);
        }
    }
    
    public void shiftRows(State s){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i; j++) {
                int rowHead = s.state[i*4];
                for (int k = 0; k < 3; k++) {
                    s.state[i*4 + k] = s.state[i*4 + k +1];
                }
                s.state[i*4+3] = rowHead;
            }
        }
    }
    
    public void subBytes(State s){
        for (int i = 0; i < 16; i++) {
            int si = s.state[i];
            int x = (si & 0xf0) >> 4;
            int y = (si & 0x0f);
            s.state[i] = SBox.getInstance().apply(x, y);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
