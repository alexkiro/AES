/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author kiro
 */
public class WPoly {

    BinPoly x3, x2, x1, x0;

    public WPoly(int a3, int a2, int a1, int a0) {
        x3 = new BinPoly(a3);
        x2 = new BinPoly(a2);
        x1 = new BinPoly(a1);
        x0 = new BinPoly(a0);
    }

    public WPoly(BinPoly a3, BinPoly a2, BinPoly a1, BinPoly a0) {
        x3 = a3;
        x2 = a2;
        x1 = a1;
        x0 = a0;
    }

    public WPoly addTo(WPoly other) {
        return new WPoly(this.x3.addTo(other.x3),
                this.x2.addTo(other.x2),
                this.x1.addTo(other.x1),
                this.x0.addTo(other.x0));

    }
//d 0 = (a0 • b0 )  ̄ (a3 • b1 )  ̄ (a 2 • b2 )  ̄ (a1 • b3 )
//d1 = (a1 • b0 )  ̄ (a 0 • b1 )  ̄ (a3 • b2 )  ̄ (a 2 • b3 )
//d 2 = (a 2 • b0 )  ̄ (a1 • b1 )  ̄ (a 0 • b2 )  ̄ (a3 • b3 )
//d 3 = (a3 • b0 )  ̄ (a 2 • b1 )  ̄ (a1 • b2 )  ̄ (a0 • b3 )

    public WPoly multiply(WPoly other) {
        BinPoly d3 = this.x3.multiply(other.x0)
                .addTo(this.x2.multiply(other.x1))
                .addTo(this.x1.multiply(other.x2))
                .addTo(this.x0.multiply(other.x3));
        BinPoly d2 = this.x2.multiply(other.x0)
                .addTo(this.x1.multiply(other.x1))
                .addTo(this.x0.multiply(other.x2))
                .addTo(this.x3.multiply(other.x3));
        BinPoly d1 = this.x1.multiply(other.x0)
                .addTo(this.x0.multiply(other.x1))
                .addTo(this.x3.multiply(other.x2))
                .addTo(this.x2.multiply(other.x3));
        BinPoly d0 = this.x0.multiply(other.x0)
                .addTo(this.x3.multiply(other.x1))
                .addTo(this.x2.multiply(other.x2))
                .addTo(this.x1.multiply(other.x3));
        return new WPoly(d3, d2, d1, d0);
    }

    public void debug() {
        System.out.println(x3.toString() + " " + x2.toString()
                + " " + x1.toString() + " " + x0.toString());
    }
}
