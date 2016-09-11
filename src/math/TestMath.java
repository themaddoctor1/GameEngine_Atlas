package math;

import math.geometry.*;

public class TestMath {
    
    public static void main(String[] args) {
        Vector v = new Vector(4, 0, 0);
        Vector axis = new Vector(0, 0, 1);

        System.out.println(Quaternion.rotateVector(v, axis, Math.PI / 2));

        Matrix m = new Matrix(3, 4);
        m.set(0, 0, 1);

        m.set(1, 0, 1);
        m.set(1, 1, 1);

        m.set(2, 1, 1);

        m.set(0, 3, 1);
        m.set(1, 3, 2);
        m.set(2, 3, 3);

        System.out.println("Input:\n" + m);

        System.out.println("Output:\n" + m.solveSystem());

    }

}
