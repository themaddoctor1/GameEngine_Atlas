
package math.geometry.shapes;

import math.Vector;
import math.geometry.*;


public class Tetrahedron extends Polyhedron {
    
    static double TEST = Math.pow(8, -15);

    public Tetrahedron(double s) {
        super(new Plane[4]);
        
        double sine = 1.0/3;
        double cosine = Math.sqrt(1 - Math.pow(sine, 2));
        //sine *= TEST;
        TEST += Math.pow(2, -16);
        for(int i = 0; i < 3; i++) {
            double theta = i*2.0*Math.PI/3;
            Vector n = new Vector(Math.cos(theta) * cosine, Math.sin(theta) * cosine, sine);
            sides[i] = new Plane(n, 1);
        }
        
        sides[3] = new Plane(new Vector( 0, 0, -1), 1);
        
        /*
        for(int i = 0; i < 4; i++) {
            System.out.println(sides[i]);
        }
        
        Vector[] verts = vertices();
        for(int i = 0; i < verts.length; i++) {
            System.out.println(verts[i]);
        }
        */

    }

}
