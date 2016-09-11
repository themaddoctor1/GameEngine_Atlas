
package graphics;

import math.Vector;
import math.Transform;

public class PerspectiveCamera extends Camera {
    
    public PerspectiveCamera() { super(); }
    public PerspectiveCamera(Transform t) { super(t); }

    public Point2D projectFrom3D(Vector vec) {
        //The position of the point in 3D space.
        Vector d = getTransform().transformVector(vec);
        
        return simpleProject(d);
    }

    public Point2D simpleProject(Vector vec) {

        Vector e = new Vector(
                getCenterPoint().X, 
                256, 
                getCenterPoint().Y);

        double x =  e.Y * vec.X / vec.Y + e.X;
        double y = -e.Y * vec.Z / vec.Y + e.Z;

        return new Point2D(x, y);
    }

}

