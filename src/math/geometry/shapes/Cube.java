
package math.geometry.shapes;

import math.Vector;
import math.geometry.*;


public class Cube extends Polyhedron {
    
    public Cube(double s) {
        super(new Plane[6]);

        sides[0] = new Plane(new Vector( 1,  0,  0), 1);
        sides[1] = new Plane(new Vector(-1,  0,  0), 1);
        sides[2] = new Plane(new Vector( 0,  1,  0), 1);
        sides[3] = new Plane(new Vector( 0, -1,  0), 1);
        sides[4] = new Plane(new Vector( 0,  0,  1), 1);
        sides[5] = new Plane(new Vector( 0,  0, -1), 1);
        
    }

}

