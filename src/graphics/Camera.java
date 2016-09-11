
package graphics;

import math.*;

public abstract class Camera extends Transformable {

    private Point2D center = new Point2D(320, 240);

    public Camera() {
        super(new Transform(new Vector(), new Quaternion(new Vector(1, 0, 0))));
    }

    public Camera(Transform t) {
        super(t);
    }

    public abstract Point2D projectFrom3D(Vector vec);
    public abstract Point2D simpleProject(Vector vec);

    public final Point2D getCenterPoint() { return center; }
    public final void setCenterPoint(Point2D p) { center = p; }

}

