
package math.geometry;

import java.util.Stack;
import math.Vector;

public class PolyCollider implements Collider {

    private Collider[] colliders;

    public PolyCollider(Collider[] cols) {
        colliders = cols;
    }

   
    public boolean pointCollides(Vector p) {
        for(int i = 0; i < colliders.length; i++)
            if(colliders[i].pointCollides(p))
                return true;
        return false;
    }

    public boolean collidesWith(Collider col) {
        for(int i = 0; i < colliders.length; i++)
            if(colliders[i].collidesWith(col))
                return true;
        return false;
    }

    public boolean hasCollidingVertex(Collider col) {
        for(int i = 0; i < colliders.length; i++)
            if(colliders[i].hasCollidingVertex(col))
                return true;
        return false;
    }
    

    public Vector[] vertices() {
        Stack<Vector> verts = new Stack<>();

        int size = 0; 
        for(int i = 0; i < colliders.length; i++) {
            Vector[] add = colliders[i].vertices();
            size += add.length;

            for(int j = 0; j < add.length; j++)
                if(pointCollides(add[j]))
                    verts.push(add[j]);
        }

        Vector[] res = new Vector[size];
        for(int i = 0; !verts.empty(); i++)
            res[i] = verts.pop();
        
        return res;

    }


}
