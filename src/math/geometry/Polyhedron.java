
package math.geometry;

import java.util.Stack;
import math.Vector;

public class Polyhedron implements Collider {

    protected Plane[] sides;

    public Polyhedron(Plane[] components) {
        sides = components;
    }

    public boolean collidesWith(Collider col) {
        
        return hasCollidingVertex(col) || col.hasCollidingVertex(this);

    }

    public boolean hasCollidingVertex(Collider col) {
        Vector[] verts = col.vertices();

        for(int i = 0; i < verts.length; i++)
            if(pointCollides(verts[i]))
                return true;
        return false;
    }

    public boolean pointCollides(Vector v) {
        for(int i = 0; i < sides.length; i++)
            if(!sides[i].pointCollides(v))
                return false;
        return true; 
    }
    
    public Plane[] planes() {
        return sides;
    }

    public void setPlanes(Plane[] p) {
        sides = p;
    }

    public Vector[] vertices() {
        Stack<Vector> verts = new Stack<>();

        int size = 0; 
        for(int i = 0; i < sides.length; i++)
            for(int j = i+1; j < sides.length; j++)
                for(int k = j+1; k < sides.length; k++) {
                    Vector v = Plane.pointOfIntersect(sides[i], sides[j], sides[k]);
                    if(!v.isInfinite() && pointCollides(v)) {
                        //System.out.println(v);
                        verts.push(v);
                        size++;
                    }
                }
        Vector[] res = new Vector[size];
        for(int i = 0; !verts.empty(); i++)
            res[i] = verts.pop();
        
        return res;

    }

}
