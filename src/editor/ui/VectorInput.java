package editor.ui;

import java.awt.event.*;
import javax.swing.*;

import graphics.*;
import math.*;
import math.geometry.*;
import math.geometry.shapes.*;

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





