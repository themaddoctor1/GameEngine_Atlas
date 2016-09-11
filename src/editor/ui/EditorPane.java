package editor.ui;

import java.awt.*;

import graphics.*;
import math.*;
import math.geometry.*;
import math.geometry.shapes.*;

public class EditorPane extends JPanel {
    
    public EditorPane(JComponent... params) {
        initWindow(params);

        setPreferredSize(new Dimension(400, 480));
    }
    
    private void initWindow(JComponent... params) {
        for(int i = 0; i < params.length; i++)
            add(params[i]);

        setVisible(true);
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        PerspectiveCamera cam = new PerspectiveCamera(new Transform(new Vector(0, -8, 0), new Quaternion(new Vector(0, 0, 0), 0)));
        cam.setCenterPoint(new Point2D(getWidth()/2, getHeight()/2));


        ShapeBuilder.getLoadedModel().draw(g, cam);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}


