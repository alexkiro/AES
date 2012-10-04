/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

/**
 *
 * @author kiro
 */
public class State {
    public int [] state = new int[16];
    public State(int [] state){
        this.state = state;
    } 
    public State(){
        
    }
    
    public WPoly collumnAsWord(int x){
        return new WPoly(state[12+x],state[8+x],state[4+x], state[x]);
    }
    
    public void wordAsCollumn(WPoly w, int x){
        state[12+x] = w.x3.poly;
        state[8+x] = w.x2.poly;
        state[4+x] = w.x1.poly;
        state[x] = w.x0.poly;
    }
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sb.append("{");
                sb.append(Integer.toHexString(state[i*4+j]));
                sb.append("} ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
}
