package editor.ui;

import java.awt.event.*;
import javax.swing.*;

import graphics.*;
import math.*;
import math.geometry.*;
import math.geometry.shapes.*;

public class QuaternionInput extends JPanel {

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
    


