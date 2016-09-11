
package graphics;

import math.*;
import math.geometry.*;

public class ColliderDraw extends Drawable {
    
    private Collider col;

    public ColliderDraw(Collider c) {
        col = c;
    }

    public void draw(Object... params) {
        Vector[] verts = col.vertices();
        
        Camera cam = (Camera) params[1];

        for(int i = 0; i < verts.length; i++) {
            Vector A = cam.getTransform().transformVector(verts[i]);
            for(int j = i+1; j < verts.length; j++) {
                Vector B = cam.getTransform().transformVector(verts[j]);
                (new Line3D(A, B)).draw(params);
            }
        }
    }

}

