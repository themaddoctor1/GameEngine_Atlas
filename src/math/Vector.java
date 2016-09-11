
package math;

public class Vector {
    
    public static final Vector
        I = new Vector(1, 0, 0),
        J = new Vector(0, 1, 0),
        K = new Vector(0, 0, 1),
        ZERO = new Vector();
        
    public final double X, Y, Z;
    
    public Vector() { this(0,0,0); }

    public Vector(double x, double y, double z) {
        X = x;
        Y = y;
        Z = z;
    }

    public Vector mul(double c) {
        return new Vector(c*X, c*Y, c*Z);
    }

    public Vector div(double c) {
        return new Vector(X/c, Y/c, Z/c);
    }

    public Vector add(Vector v) {
        return new Vector(X + v.X, Y + v.Y, Z + v.Z); 
    }

    public Vector sub(Vector v) {
        return new Vector(X - v.X, Y - v.Y, Z - v.Z);
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2) + Math.pow(Z, 2));
    }

    public double dot(Vector v) {
        return X*v.X + Y*v.Y + Z*v.Z;
    }

    public Vector cross(Vector v) {
        return new Vector(
            Y*v.Z - Z*v.Y,
            X*v.Z - Z*v.X,
            X*v.Y - Y*v.X);
    }
    
    public Vector unitVector() {
        return mul(1 / magnitude());
    }
    
    public boolean isInfinite() {
        return
                Double.isInfinite(X) ||
                Double.isInfinite(Y) ||
                Double.isInfinite(Z) ||
                Double.isNaN(X) ||
                Double.isNaN(Y) ||
                Double.isNaN(Z);
    }
    
    public boolean equals(Vector v) {
        return X == v.X && Y == v.Y && Z == v.Z;
    }

    public String toString() {
        return "<" + X + ", " + Y + ", " + Z + ">";
    }

}


