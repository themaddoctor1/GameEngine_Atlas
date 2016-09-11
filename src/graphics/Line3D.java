
package graphics;

import java.awt.Graphics2D;

import math.Transform;
import math.Vector;

public class Line3D extends Drawable {
    
    public final Vector A, B;
    
    public Line3D(Vector a, Vector b) {
        this(a, b, new Transform());
    }

    public Line3D(Vector a, Vector b, Transform t) {
        super(t);
        A = a;
        B = b;
    }
    
    /**
     * Draws a line based on a camera and graphics system.
     *
     * @param params the set of parameters.
     * @param params[0] the graphics package.
     * @param params[1] the camera. 
     */
    public void draw(Object... params) {
        Camera cam = (Camera) params[1];
        
        Vector u = cam.getTransform().transformVector(transform.transformVector(A));
        Vector v = cam.getTransform().transformVector(transform.transformVector(B));
        
        if(u.Y <= 0 && v.Y <= 0)
            return;
        else if(v.Y <= 0) {
            double error = -v.Y + 0.0625;
            Vector diff = u.sub(v);
            diff = diff.mul(error / diff.Y);
            v = u.add(diff);
        } else if(u.Y <= 0) {
            double error = -u.Y + 0.0625;
            Vector diff = v.sub(u);
            diff = diff.mul(error / diff.Y);
            v = v.add(diff);
        }

        Point2D a = cam.simpleProject(u);
        Point2D b = cam.simpleProject(v);
        
        if(a.X < 0 || a.X >= 2*cam.getCenterPoint().X
        || a.Y < 0 || a.Y >= 2*cam.getCenterPoint().Y
        || b.X < 0 || b.X >= 2*cam.getCenterPoint().X
        || b.Y < 0 || b.Y >= 2*cam.getCenterPoint().Y)
            return;
        
        //Draw line from a to b on the screen.
        Graphics2D g2 = (Graphics2D) params[0];
        g2.drawLine((int) a.X, (int) a.Y, (int) b.X, (int) b.Y);
    }

}

