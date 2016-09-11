
package math;

public class Quaternion {
    
    private Matrix matrix;
    private static Matrix
        C = null,
        I = null,
        J = null,
        K = null;
    
    /**
     * Creates a Quaternion based on a VALID Quaternion matrix.
     *
     * @param m A VALID Matrix representation of a Quaternion.
     */
    public Quaternion(Matrix m) {
        matrix = m;
    }
    
    /**
     * Creates a Quaternion equal to a + bi + cj + dk.
     *
     * @param a The scalar component.
     * @param b The i component.
     * @param c The j component.
     * @param d The k component.
     */
    public Quaternion(double a, double b, double c, double d) {
        if(C == null) {
            C = new Matrix(4, 4);
            for(int i = 0; i < 4; i++)
                C.set(i, i, 1);
        } if(I == null) {
            I = new Matrix(4, 4);
            I.set(1, 0, -1);
            I.set(0, 1, 1);
            I.set(3, 2, 1);
            I.set(2, 3, -1);
        } if(J == null) {
            J = new Matrix(4, 4);
            J.set(2, 0, -1);
            J.set(3, 1, -1);
            J.set(0, 2, 1);
            J.set(1, 3, 1);
        } if(K == null) {
            K = new Matrix(4, 4);
            for(int i = 0; i < 4; i++)
                K.set(3-i, i, i%2 == 0 ? -1 : 1);
        }

        matrix = new Matrix(4, 4)
                .add(C.mul(a))
                .add(I.mul(b))
                .add(J.mul(c))
                .add(K.mul(d));
    }
    
    /**
     * Creates a pure vector Quaternion.
     */
    public Quaternion(double i, double j, double k) {
        this(0, i, j, k);
    }
    
    public Quaternion(Vector v) {
        this(0, v.X, v.Y, v.Z);
    }

    /**
     * Creates a rotation Quaternion.
     */
    public Quaternion(Vector v, double theta) {
        this(
                Math.cos(theta/2), 
                v.X * Math.sin(theta/2),
                v.Y * Math.sin(theta/2),
                v.Z * Math.sin(theta/2));
    }

    public Quaternion add(Quaternion q) {
        return new Quaternion(matrix.add(q.matrix));
    }

    public Quaternion conjugate() {
        return new Quaternion(matrix.transpose());
    }

    public Quaternion hamiltonian(Quaternion q) {

        return new Quaternion(matrix.mul(q.matrix));

    }

    public Quaternion mul(double d) {
        return new Quaternion(matrix.mul(d));
    }

    public double norm() {
        return Math.sqrt(
                    Math.pow(A(),2) + Math.pow(B(), 2)
                    + Math.pow(C(), 2) + Math.pow(D(), 2));
    }

    public Quaternion reciprocal() {
        return conjugate().mul(1 / Math.pow(norm(), 2));
    }

    /**
     * Rotates Vector p around u by an angle theta.
     */
    public static Vector rotateVector(Vector p, Vector u, double theta) {
        Quaternion q = new Quaternion(u, theta);
        
        return rotateVector(p, q);
    }

    public static Vector rotateVector(Vector p, Quaternion q) {
        return q.hamiltonian(new Quaternion(p)).hamiltonian(q.conjugate()).vector();
    }

    public Quaternion sub(Quaternion q) {
        return new Quaternion(matrix.sub(q.matrix));
    }

    public Quaternion unit() {
        return mul(1 / norm());
    }

    public double A() { return matrix.get(0, 0); }
    public double B() { return matrix.get(0, 1); }
    public double C() { return matrix.get(0, 2); }
    public double D() { return matrix.get(0, 3); }
    public Vector vector() { return new Vector(B(), C(), D()); }


    public String toString() {
        return "<" + A() + ", " + B() + ", " + C() + ", " + D() + ">";
    }

}

