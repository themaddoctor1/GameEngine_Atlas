package editor;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import graphics.*;
import math.*;
import math.geometry.*;
import math.geometry.shapes.*;

public class Editor extends JFrame {
    
    private JComponent[] comps;
    protected JComponent[] getUserComponents() { return comps; }
    
    public static final Font
        SMALL_FONT = new Font("Arial", Font.PLAIN, 12),
        MEDIUM_FONT = new Font("Arial", Font.PLAIN, 14),
        LARGE_FONT = new Font("Arial", Font.PLAIN, 16),
        SMALL_TITLE_FONT = new Font("Arial", Font.BOLD, 14),
        TITLE_FONT = new Font("Arial", Font.BOLD, 16),
        BUTTON_FONT = SMALL_FONT;

    public Editor(JComponent[] params, String[] layouts) {
        comps = params;
        initWindow(params, layouts);
    }

    private void initWindow(JComponent[] params, String[] layouts) {
        for(int i = 0; i < params.length; i++) {
            if (layouts[i] != null)
                add(params[i], layouts[i]);
            else
                add(params[i]);
        }

        setTitle("Atlas Editor");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        JMenuBar bar = createMenuBar();
        setJMenuBar(bar);

        setVisible(true);
    }

    public JMenuBar createMenuBar() {
        
        JMenuBar topBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem open = new JMenuItem("Open");
        open.setMnemonic(KeyEvent.VK_O);
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String txt = ((JMenuItem)evt.getSource()).getText();
                EditorLoadWindow elw = new EditorLoadWindow();
            }   
        });
        fileMenu.add(open);

        JMenuItem newModel = new JMenuItem("New");
        newModel.setMnemonic(KeyEvent.VK_N);
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ShapeBuilder.newModel();
        }});
        fileMenu.add(newModel);
        
        JMenuItem save = new JMenuItem("Save");
        save.setMnemonic(KeyEvent.VK_S);
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String txt = ((JMenuItem)evt.getSource()).getText();
        }});
        fileMenu.add(save);

        JMenuItem exit = new JMenuItem("Quit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
        }});
        fileMenu.add(exit);
        
        
        topBar.add(fileMenu);
        
        return topBar;

    }

    public static void main(String[] args) {
        
        Editor editor = new Editor(
                new JComponent[]{
                    new EditorBoard(),
                    new EditorBar()
                }, new String[]{
                    BorderLayout.CENTER,
                    BorderLayout.EAST
                }

        );

        while(true)
            editor.repaint();

    }


}

class EditorBar extends JScrollPane {
    
    private JPanel panel;
    protected ArrayList<ShapeSection> shapeDialogs;

    public EditorBar() {
        super(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        shapeDialogs = new ArrayList<>();
         
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        getViewport().add(panel);
        

        JButton addPoly = new JButton("Add Poly");
        //addPoly.setAlignmentX(RIGHT_ALIGNMENT);
        panel.add(addPoly);
        
        loadModel(ShapeBuilder.getLoadedModel());
        
        //setPreferredSize(new Dimension(240, 480));
        //setAlignmentX(RIGHT_ALIGNMENT);
        setVisible(true);
    }
    
    public void loadModel(MultiShape3D model) {
        for(Shape3D s : model.getShapes())
            loadSubshape(s);
    }

    public void loadSubshape(Shape3D model) {
        ShapeSection ss = new ShapeSection(model, this);
        shapeDialogs.add(ss);
        //ss.setAlignmentX(RIGHT_ALIGNMENT);
        panel.add(ss);
    }

    public void addSubshape(Shape3D model) {
        Shape3D[] shapes = new Shape3D[shapeDialogs.size()+1];
        for(int i = 0; i < shapes.length - 1; i++)
            shapes[i] = shapeDialogs.get(i).myShape;
        shapes[shapes.length-1] = model;
        ShapeBuilder.setLoadedModel(new MultiShape3D(shapes));
        
        loadSubshape(model);
    }

    public void removeSubshape(int idx) {
        Shape3D[] shapes = new Shape3D[shapeDialogs.size()-1];
        for(int i = 0; i < idx; i++)
            shapes[i] = shapeDialogs.get(i).myShape;
        for(int i = idx+1; i < shapeDialogs.size(); i++)
            shapes[i-1] = shapeDialogs.get(i).myShape;
        ShapeBuilder.setLoadedModel(new MultiShape3D(shapes));

        panel.remove(shapeDialogs.get(idx));
        shapeDialogs.remove(idx);
    }
    
    /**
     * Used for holding UI stuff for whole shapes
     */
    class ShapeSection extends JPanel {
        public Shape3D myShape;
        private ArrayList<PlaneSection> planes; 
        protected EditorBar master;

        public ShapeSection(EditorBar m) {
            this(new Shape3D(new Polyhedron(new Plane[0])), m);
        }
        
        public ShapeSection(Shape3D s, EditorBar m) {
            master = m;

            setBorder(BorderFactory.createLineBorder(Color.GREEN));

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            //setLayout(new FlowLayout());

            planes = new ArrayList<PlaneSection>();
            
            JLabel title = new JLabel("Polyhedron");
            title.setFont(Editor.TITLE_FONT);
            //title.setAlignmentX(RIGHT_ALIGNMENT);
            add(title);
             
            //top.setPreferredSize(new Dimension(240, 30));
            
            //Pos input
            VectorInput posInput = new VectorInput("Position");
            add(posInput);
            //posInput.setAlignmentX(RIGHT_ALIGNMENT);
            
            //Rot input
            QuaternionInput rotInput = new QuaternionInput("Rotation");
            add(rotInput);
            //rotInput.setAlignmentX(RIGHT_ALIGNMENT);

            JPanel top = new JPanel();
            FlowLayout topLayout = new FlowLayout();
            top.setLayout(topLayout);

            JButton addSide = new JButton("Add Side");
            addSide.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    addPlane(new Plane(new Vector(1, 0, 0), 0));
                    revalidate();
                }
            });
            addSide.setFont(Editor.BUTTON_FONT);
            top.add(addSide);

            JButton remShp = new JButton("Remove");
            remShp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    System.out.println(this);
                    master.removeSubshape(master.shapeDialogs.indexOf(this));
                }
            });
            remShp.setFont(Editor.BUTTON_FONT);
            top.add(remShp);
            
            //top.setAlignmentX(RIGHT_ALIGNMENT);
            add(top);

            myShape = s;
            
            Plane[] ps = s.getPoly().planes();
            for(Plane p : ps)
                loadPlane(p);
        }

        public void editPlane(int idx, Vector n, double d) {
            myShape.getPoly().planes()[idx] = new Plane(n, d);
        }

        public void loadPlane(Plane p) {
            PlaneSection ps = new PlaneSection(this);
            add(ps);
            planes.add(ps);
        }

        public void addPlane(Plane p) {
            Plane[] ps = new Plane[myShape.getPoly().planes().length + 1];
            for(int i = 0; i < ps.length-1; i++)
                ps[i] = myShape.getPoly().planes()[i];
            ps[ps.length-1] = p;
            int idx = master.shapeDialogs.indexOf(this);
            
            ShapeBuilder.getLoadedModel().getShapes()[idx].getPoly().setPlanes(ps);
             
            loadPlane(p);
        }

        public void removePlane(int idx) {

        }

        /**
         * Used for holding UI stuff for planess.
         */
        class PlaneSection extends JPanel {
            private ShapeSection master;

            public PlaneSection(ShapeSection m) {
                master = m;
                
                setBorder(BorderFactory.createLineBorder(Color.RED));
    
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                JLabel title = new JLabel("Plane");
                //tempLabel.setAlignmentX(CENTER_ALIGNMENT);
                title.setFont(Editor.TITLE_FONT);
                add(title);

                VectorInput normal = new VectorInput("Normal");
                add(normal);

                JTextField D = new JTextField(8);
                add(D);
            }
        }
    }

}

class EditorBoard extends JPanel {
    
    public EditorBoard(JComponent... params) {
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


class EditorLoadWindow extends JFrame {


    public EditorLoadWindow() {
        initWindow();
    }
    
    private void initWindow() {
        

        setTitle("Load File");
        setSize(480, 24);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JFrame frame = this;

        JTextField filebar = new JTextField(32);
        add(filebar, BorderLayout.CENTER);

        JButton load = new JButton("Load");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println(filebar.getText());
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); 
            }
        });
        add(load, BorderLayout.EAST);

        setVisible(true);
    }
    
   
}



class QuaternionInput extends JPanel {

    private JButton apply;
    private JTextField W, X, Y, Z;

    public QuaternionInput(String name) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setPreferredSize(new Dimension(240, 240));
        setBorder(BorderFactory.createLineBorder(Color.blue));

        JLabel vecTitle = new JLabel(name + " - Quaternion");
        vecTitle.setFont(Editor.MEDIUM_FONT);
        //vecTitle.setAlignmentX(RIGHT_ALIGNMENT);
        add(vecTitle);
         
        W = new JTextField(8);
        W.setMaximumSize(new Dimension(120, 30));
        X = new JTextField(8);
        X.setMaximumSize(new Dimension(120, 30));
        Y = new JTextField(8);
        Y.setMaximumSize(new Dimension(120, 30));
        Z = new JTextField(8);
        Z.setMaximumSize(new Dimension(120, 30));
        
        JPanel w = new JPanel(),
               x = new JPanel(), 
               y = new JPanel(),
               z = new JPanel();
        w.setLayout(new FlowLayout());// w.setMaximumSize(new Dimension(150, 30));
        x.setLayout(new FlowLayout());// x.setMaximumSize(new Dimension(150, 30));
        y.setLayout(new FlowLayout());// y.setMaximumSize(new Dimension(150, 30));
        z.setLayout(new FlowLayout());// z.setMaximumSize(new Dimension(150, 30));

        w.add(new JLabel("W")); 
        x.add(new JLabel("X"));
        y.add(new JLabel("Y"));
        z.add(new JLabel("Z"));
        w.add(W); x.add(X); y.add(Y); z.add(Z);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(w); panel.add(x); panel.add(y); panel.add(z);
        add(panel);
        //panel.setAlignmentX(RIGHT_ALIGNMENT);

        apply = new JButton("Apply");
        apply.setAlignmentX(RIGHT_ALIGNMENT);
        apply.setFont(Editor.BUTTON_FONT);
        add(apply);

    }
}
    
class VectorInput extends JPanel {

    private JButton apply;
    private JTextField X, Y, Z;

    public VectorInput(String name) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setAlignmentX(RIGHT_ALIGNMENT);
        
        setBorder(BorderFactory.createLineBorder(Color.blue));

        JLabel vecTitle = new JLabel(name + " - Vector");
        vecTitle.setFont(Editor.MEDIUM_FONT);
        //vecTitle.setAlignmentX(LEFT_ALIGNMENT);
        add(vecTitle);
         
        X = new JTextField(8);
        //X.setMaximumSize(new Dimension(120, 30));
        Y = new JTextField(8);
        //Y.setMaximumSize(new Dimension(120, 30));
        Z = new JTextField(8);
        //Z.setMaximumSize(new Dimension(120, 30));
         
        JPanel x = new JPanel(), 
               y = new JPanel(),
               z = new JPanel();
        x.setLayout(new FlowLayout());
        //x.setMaximumSize(new Dimension(150, 30));
        //x.setAlignmentX(RIGHT_ALIGNMENT);
        
        y.setLayout(new FlowLayout());
        //y.setMaximumSize(new Dimension(150, 30));
        //y.setAlignmentX(RIGHT_ALIGNMENT);
        
        z.setLayout(new FlowLayout());
        //z.setAlignmentX(RIGHT_ALIGNMENT);
        //z.setMaximumSize(new Dimension(150, 30));


        x.add(new JLabel("X"));
        y.add(new JLabel("Y"));
        z.add(new JLabel("Z"));
        x.add(X); y.add(Y); z.add(Z);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(x); panel.add(y); panel.add(z);
        //x.setBorder(BorderFactory.createLineBorder(Color.gray));
        add(panel);
        //panel.setAlignmentX(RIGHT_ALIGNMENT);

        apply = new JButton("Apply");
        apply.setAlignmentX(RIGHT_ALIGNMENT);
        apply.setFont(Editor.BUTTON_FONT);
        add(apply); 
    }
}



