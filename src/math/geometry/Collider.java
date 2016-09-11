
package math.geometry;

import math.Vector;

public interface Collider {

    public boolean pointCollides(Vector p);

    public boolean collidesWith(Collider col);
    public boolean hasCollidingVertex(Collider col);
    
    public Vector[] vertices();

}
