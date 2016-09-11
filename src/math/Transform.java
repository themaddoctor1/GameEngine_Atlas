
package math;

import math.Quaternion;
import math.Vector;

/**
 * A Transform is a representation of a frame of reference
 * in physical simulation.
 */
public class Transform {
    //The reference frame's position in space.
    private Vector position;

    //The rotation relative to the containing frame.
    private Quaternion rotation;
    
    public Transform() {
        this(new Vector(), new Quaternion(new Vector(0, 0, 1), 0));
    }

    public Transform(Vector pos, Quaternion rot) {
        position = pos;
        rotation = rot;
    }

    public Vector getPos() { return position; }
    public void setPos(Vector pos) { position = pos; }

    public Quaternion getRot() { return rotation; }
    public void setRot(Quaternion rot) { rotation = rot; }

    /**
     * Translates the vector into the reference frame's space.
     *
     * @param vec The input Vector, which is assumed to be
     *            origination in default world space.
     */
    public Vector transformVector(Vector vec) {
        Vector calc = vec.sub(position);
        
        Vector result = Quaternion.rotateVector(calc, rotation);
        
        return result;
        //return result.unitVector().mul(calc.magnitude());
    }
    
    public Vector undoVecTransform(Vector vec) {
        Vector calc = Quaternion.rotateVector(vec, rotation.conjugate());

        return calc.add(position);
    }

    public Quaternion transformQuaternion(Quaternion q) {
        return q.hamiltonian(rotation);
    }

    public Transform transformTransform(Transform rf) {
        return new Transform(
                transformVector(position),
                transformQuaternion(rotation));
    }

    public Transform inverse() {
        return new Transform(
                position.mul(-1),
                rotation.conjugate());
    }


}

