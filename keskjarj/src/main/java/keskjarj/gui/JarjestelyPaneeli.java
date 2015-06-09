/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author mkahri
 */
public class JarjestelyPaneeli extends JPanel {

    Rectangle rect;
    int preX, preY;
    boolean isFirstTime = true;
    Rectangle area;
    boolean pressOut = false;
    private Dimension koko = new Dimension(1300, 800);

    public JarjestelyPaneeli() 
    {
        this.rect = new Rectangle(0, 0, 100, 40);

        setBackground(Color.white);
        addMouseMotionListener(new MyMouseAdapter());
        addMouseListener(new MyMouseAdapter());

        setPreferredSize(new Dimension());

        JLabel l1 = new JLabel("Layout Manager Demonstration\n\n");
        JLabel l2 = new JLabel("Choose a tab to see an example of "
                + "a layout manager.");

        add(l1);

        add(l2);
    }

 

    @Override
    public Dimension getPreferredSize() {
        return koko;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        if (isFirstTime) {
            area = new Rectangle(koko);
            rect.setLocation(50, 50);
            isFirstTime = false;
        }

        g2d.setColor(Color.black);
        g2d.fill(rect);
    }

    boolean checkRect() {
        if (area == null) {
            return false;
        }

        if (area.contains(rect.x, rect.y, rect.getWidth(), rect.getHeight())) {
            return true;
        }

        int new_x = rect.x;
        int new_y = rect.y;

        if ((rect.x + rect.getWidth()) > area.getWidth()) {
            new_x = (int) area.getWidth() - (int) (rect.getWidth() - 1);
        }
        if (rect.x < 0) {
            new_x = -1;
        }
        if ((rect.y + rect.getHeight()) > area.getHeight()) {
            new_y = (int) area.getHeight() - (int) (rect.getHeight() - 1);
        }
        if (rect.y < 0) {
            new_y = -1;
        }
        rect.setLocation(new_x, new_y);
        return false;
    }

    private class MyMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            preX = rect.x - e.getX();
            preY = rect.y - e.getY();

            if (rect.contains(e.getX(), e.getY())) {
                updateLocation(e);
            } else {
                pressOut = true;
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!pressOut) {
                updateLocation(e);
            } else {
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (rect.contains(e.getX(), e.getY())) {
                updateLocation(e);
            } else {
                pressOut = false;
            }
        }

        public void updateLocation(MouseEvent e) {
            rect.setLocation(preX + e.getX(), preY + e.getY());
            checkRect();

            repaint();
        }
    }
}
