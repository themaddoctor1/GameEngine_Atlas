
package graphics;

public class MultiShape3D extends Drawable {
    
    private Shape3D[] shapes;

    public MultiShape3D(Shape3D... s) {
        super();
        shapes = s;
    }

    public void draw(Object... params) {
        for (int i = 0; i < shapes.length; i++)
            shapes[i].draw(new Object[]{ params[0], params[1], getTransform()});
    }

    public Shape3D[] getShapes() { return shapes; }

}

