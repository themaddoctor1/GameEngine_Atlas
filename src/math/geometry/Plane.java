
package math.geometry;

import math.Matrix;
import math.Vector;

public class Plane {
    
    public static final double TOLERABLE_ERROR = Math.pow(2, -14);

    //Follows Ax + By + Cz - D = f(v)
    //Meaning that plane is on Ax + By + Cz = D

    public final Vector N;
    public final double D;

    public Plane(Vector n, double d) {
        N = n;
        D = d;
    }

    public Plane(Vector n, Vector p) {
        N = n;
        D = N.dot(p);
    }

    public double valueOf(Vector v) {
        return N.dot(v) - D;
    }

    public static Vector pointOfIntersect(Plane a, Plane b, Plane c) {
        
        Matrix m = new Matrix(3, 4);
        m.set(0,0,a.N.X);
        m.set(0,1,a.N.Y);
        m.set(0,2,a.N.Z);
        m.set(0,3,a.D);
        m.set(1,0,b.N.X);
        m.set(1,1,b.N.Y);
        m.set(1,2,b.N.Z);
        m.set(1,3,b.D);
        m.set(2,0,c.N.X);
        m.set(2,1,c.N.Y);
        m.set(2,2,c.N.Z);
        m.set(2,3,c.D);

        Matrix solution = m.solveSystem();
        
        return new Vector(solution.get(0,3), solution.get(1,3), solution.get(2,3));
    }

    public boolean pointCollides(Vector p) {
        return valueOf(p) <= TOLERABLE_ERROR;
    }

    public boolean pointOnSurface(Vector p) {
        return Math.abs(valueOf(p)) <= TOLERABLE_ERROR;
    }
    
    public String toString() {
        return "N = " + N + "\n" + "D = " + D;
    }

}
