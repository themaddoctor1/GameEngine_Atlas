
package math.geometry.shapes;

import math.*;
import math.geometry.*;

public class Sphere extends Polyhedron {
    
    public static final int ACCURACY = 4;

    public Sphere(double s) {
        super(initShell(s));

        Vector[] verts = vertices();

        for(int i = 0; i < verts.length; i++)
            System.out.println(verts[i]);
        System.out.println("\n\n\n\n");

    }

    private static Plane[] initShell(double s) {
        Plane[] result = new Plane[12*ACCURACY-6];
        
        int i = 0;
        
        for(int j = 0; j < ACCURACY; j++) {
            double angle = (j / (ACCURACY + 1.0)) * Math.PI / 2;
            double sine = Math.sin(angle);
            double cosine = Math.cos(angle);
            
            result[i++] = new Plane(new Vector( sine,    cosine, 0), 1.0);
            result[i++] = new Plane(new Vector( cosine, -sine, 0), 1.0);
            result[i++] = new Plane(new Vector(-sine,   -cosine, 0), 1.0);
            result[i++] = new Plane(new Vector(-cosine,  sine, 0), 1.0);
            
            if(j != 0) { 
                result[i++] = new Plane(new Vector(0,  sine,  cosine), 1);
                result[i++] = new Plane(new Vector(0,  sine, -cosine), 1);
                result[i++] = new Plane(new Vector(0, -sine,  cosine), 1);
                result[i++] = new Plane(new Vector(0, -sine, -cosine), 1);
                result[i++] = new Plane(new Vector( sine, 0,  cosine), 1);
                result[i++] = new Plane(new Vector(-sine, 0, -cosine), 1);
            }

            result[i++] = new Plane(new Vector(-sine, 0,  cosine), 1);
            result[i++] = new Plane(new Vector( sine, 0, -cosine), 1);
        }

        return result;
        
    }

}
