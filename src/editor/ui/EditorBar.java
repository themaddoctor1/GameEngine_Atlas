package graphics.ui;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import graphics.*;
import math.*;
import math.geometry.*;
import math.geometry.shapes.*;

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



