
package graphics;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import math.*;
import math.geometry.*;
import math.geometry.shapes.*;

public class Window extends JFrame {
    
    private DrawingBoard board;
    public DrawingBoard getDrawingBoard() { return board; }

    private void initWindow() {
        add(board = new DrawingBoard());

        setTitle("Title");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setVisible(true);
    }

    public Window() {
        initWindow();


    }

    public static void main(String[] args) {
        Window w = new Window();
        Shape3D A = new Shape3D(new Tetrahedron(1));
        A.getTransform().setPos(new Vector(0, 0, 1));
        
        MultiShape3D C = new MultiShape3D(new Shape3D[]{ A });

        
        w.board.shape = C;
        /*
        Window x = new Window();
        x.board.shape = new Shape3D(new Cube(1));
        */
        while(true) {
            w.repaint();
            //x.repaint();
        }

                    
    }

}

class DrawingBoard extends JPanel {
    
    public Drawable shape;

    double disp = Math.PI * 0;
    long lastTime = System.nanoTime();
    double total = 0;
    int frames = -1;

    private void doDrawing(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.BLACK);
        
        long time = System.nanoTime();
        double delta = (time - lastTime) * Math.pow(10, -9);
        lastTime = time;
        
        if(frames >= 0) {
            total += delta;
        
            g2.drawString("FPS: " + (int)(1.0 / delta), 40, 40);
            g2.drawString("Average: " + (frames / total), 40, 60);
        }
        frames++;
        
        Transform t = 
                new Transform(
                        new Vector(/*Math.sin(disp), Math.cos(disp)*/0, 1, 0).mul(-8), 
                        new Quaternion(new Vector(0, 0, 1), 0));

        Camera cam = new PerspectiveCamera(t);
        cam.setCenterPoint(new Point2D(320, 240));
        
        shape.getTransform().setRot(
                new Quaternion(new Vector(0, 0, 1).unitVector(), disp));
        
        ((MultiShape3D) shape).getShapes()[0].getTransform().setRot(
                new Quaternion(new Vector(0, 1, 0).unitVector(), disp));

        shape.draw(g2, cam);
        

        disp = (disp + 1 / Math.pow(2, 12)) ;//% (2 * Math.PI);
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

}

