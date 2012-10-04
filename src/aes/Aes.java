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
        primaryStage.show();
        BinPoly a = new BinPoly(0x57);       
        BinPoly b = new BinPoly(0x13);
       
       // a.multiply(b).debug();
        WPoly x = new WPoly(3, 1, 1, 2);
        WPoly y= new WPoly(1, 0, 0, 0);
        x.multiply(y).debug();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
