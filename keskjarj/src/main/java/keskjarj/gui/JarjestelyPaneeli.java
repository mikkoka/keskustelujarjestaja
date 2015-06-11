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

    Rectangle suorakaide;
    int preX, preY;
    boolean ekaKerta = true;
    Rectangle alue;
    boolean pressOut = false;
    private Dimension koko = new Dimension(1300, 800);

    public JarjestelyPaneeli(Dimension koko) 
    {
        this.suorakaide = new Rectangle(0, 0, 100, 40);

//        setBackground(Color.lightGray);
        addMouseMotionListener(new HiiriAdapteri());
        addMouseListener(new HiiriAdapteri());

        setPreferredSize(koko);

        JLabel l1 = new JLabel("Hehe hehe");
        JLabel l2 = new JLabel("Hoho hoho");

        add(l1);
        add(l2);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        if (ekaKerta) {
            alue = new Rectangle(koko);
            suorakaide.setLocation(50, 50);
            ekaKerta = false;
        }

        g2d.setColor(Color.black);
        g2d.fill(suorakaide);
    }

    boolean checkRect() {
        if (alue == null) {
            return false;
        }

        if (alue.contains(suorakaide.x, suorakaide.y, suorakaide.getWidth(), suorakaide.getHeight())) {
            return true;
        }

        int new_x = suorakaide.x;
        int new_y = suorakaide.y;

        if ((suorakaide.x + suorakaide.getWidth()) > alue.getWidth()) {
            new_x = (int) alue.getWidth() - (int) (suorakaide.getWidth() - 1);
        }
        if (suorakaide.x < 0) {
            new_x = -1;
        }
        if ((suorakaide.y + suorakaide.getHeight()) > alue.getHeight()) {
            new_y = (int) alue.getHeight() - (int) (suorakaide.getHeight() - 1);
        }
        if (suorakaide.y < 0) {
            new_y = -1;
        }
        suorakaide.setLocation(new_x, new_y);
        return false;
    }

    private class HiiriAdapteri extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            preX = suorakaide.x - e.getX();
            preY = suorakaide.y - e.getY();

            if (suorakaide.contains(e.getX(), e.getY())) {
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
            if (suorakaide.contains(e.getX(), e.getY())) {
                updateLocation(e);
            } else {
                pressOut = false;
            }
        }

        public void updateLocation(MouseEvent e) {
            suorakaide.setLocation(preX + e.getX(), preY + e.getY());
            checkRect();

            repaint();
        }
    }
}
