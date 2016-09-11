package editor;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import editor.ui;

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
                    new EditorPane(),
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



