/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

/**
 *
 * @author kiro
 */
public class BinPoly {

    public int poly;

    public BinPoly(int poly) {
        this.poly = poly;
    }

    public BinPoly(String binaryPolynomial) {
        if (binaryPolynomial.length() == 8) {
            Byte.parseByte(binaryPolynomial, 2);
        }
    }

    public BinPoly addTo(BinPoly other) {
        return new BinPoly(this.poly ^ other.poly);
    }
    
    public BinPoly addTo(int other) {
        return new BinPoly(this.poly ^ other);
    }
    
    public void addToSelf(int other){
        this.poly = this.poly ^ other;
    }

    public BinPoly xtime() {
        int p = this.poly << 1;
        if (p > 255) {
            p = (p ^ 0x1B) & 0xFF;
        }
        return new BinPoly(p);
    }

    public BinPoly xtime(int times) {
        int p = this.poly;
        for (int i = 0; i < times; i++) {
            p = p << 1;
            if (p > 255) {
                p = (p ^ 0x1B) & 0xFF;
            }
        }
        return new BinPoly(p);
    }
    
    public BinPoly multiply(BinPoly other){
        int p = this.poly;
        int q = other.poly;
        BinPoly result = new BinPoly(0);
        int mask = 1; // 0000 0001 - mask used to check bites
        for (int i = 0; i < 8; i++) {
            if ((mask & q) != 0){ //if bite checks
                result.addToSelf(p);
                
            }
            p = p << 1;
            if (p > 255) {
                p = (p ^ 0x1B) & 0xFF;
            }            
            mask = mask << 1;
        }
        return result;
    }

    public void debug() {
        System.out.println("{" + Integer.toHexString(poly) + "} " + poly);
        System.out.println(Integer.toBinaryString(poly));
    }

    @Override
    public String toString() {
        return Integer.toHexString(poly);
    }
    
    
}
