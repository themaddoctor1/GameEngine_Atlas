
package graphics;

import math.Transform;
import math.Transformable;

public abstract class Drawable extends Transformable {
    
    public Drawable() { super(new Transform()); }
    public Drawable(Transform t) { super(t); }

    public abstract void draw(Object... params);

}

