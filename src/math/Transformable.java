
package math;

public class Transformable {

    protected Transform transform;

    public Transformable(Transform t) {
        transform = t;
    }
    
    public void setTransform(Transform t) { transform = t; }
    public Transform getTransform() { return transform; }

}

