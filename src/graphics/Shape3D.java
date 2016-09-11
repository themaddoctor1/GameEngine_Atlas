
package graphics;

import java.util.Stack;

import math.*;
import math.geometry.*;

public class Shape3D extends Drawable {
    
    private Polyhedron poly;
    private int[][] vertIdx;

    public Shape3D(Polyhedron p) {
        super();
        poly = p;

        Plane[] planes = p.planes();
        Vector[] verts = p.vertices();
        
        vertIdx = new int[planes.length][];
        for( int i = 0; i < vertIdx.length; i++) {
            Stack<Integer> vals = new Stack<>();
            for (int j = 0; j < verts.length; j++) {
                if (planes[i].pointOnSurface(verts[j]))
                    vals.push(j);
            }

            vertIdx[i] = new int[vals.size()];
            for (int j = 0; !vals.isEmpty(); j++) {
                vertIdx[i][j] = vals.pop();
            }
        }

    }

    public void draw(Object... params) {
        Vector[] verts = poly.vertices();
        
        Camera cam = ((Camera) params[1]);
        Transform camT = cam.getTransform();
        
        Transform defT = (params.length > 2)
                            ? (Transform) params[2]
                            : new Transform();
         
        for (int i = 0; i < vertIdx.length; i++) {
            if (true) { //Don't draw backs
                /*
                 *Currently, this is the default transform, but
                 *in the future, it will represent the transform
                 * of the shape.
                 */
                
                Vector n = poly.planes()[i].N;
                
                n = Quaternion.rotateVector(n, defT.getRot());
                n = Quaternion.rotateVector(n, transform.getRot());
                n = Quaternion.rotateVector(n, camT.getRot());

                Vector d = (poly.planes()[i].N.unitVector().mul(
                            poly.planes()[i].D / 
                            poly.planes()[i].N.magnitude()));

                //First, transform out of shape space to world space.
                d = defT.transformVector(d);
                d = transform.transformVector(d);
                
                //Then, transform into camera space from world space.
                d = camT.transformVector(d);

                if (n.dot(d) > 0)
                    continue;
            }
            
            int[] list = vertIdx[i];
            for (int j = 0; j < list.length; j++) {
                Vector A = transform.transformVector(
                        defT.transformVector(
                                verts[list[j]]));
                for (int k = j+1; k < list.length; k++) {
                    //System.out.println("(" + list[j] + ", " + list[k] + ")");
                    Vector B = transform.transformVector(
                            defT.transformVector(
                                    verts[list[k]]));
                    (new Line3D(A, B)).draw(params);
                }
            }
        }
    }
    
    public Polyhedron getPoly() { return poly; }

}

