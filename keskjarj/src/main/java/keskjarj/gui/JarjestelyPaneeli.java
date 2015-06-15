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

    Rectangle[] suorakaiteet;
    int preX, preY;
    boolean ekaKerta = true;
    Rectangle alue;
    boolean pressOut = false;
    private Dimension koko;
    int valittuna;

    public JarjestelyPaneeli(Dimension koko) 
    {
        this.koko = koko = koko;
        this.suorakaiteet = new Rectangle[2];
        suorakaiteet[0] = new Rectangle(0, 0, 200, 22);
        suorakaiteet[1] = new Rectangle(50, 50, 200, 22);

//        setBackground(Color.lightGray);
        addMouseMotionListener(new HiiriAdapteri());
        addMouseListener(new HiiriAdapteri());

        setPreferredSize(koko);

        JLabel l1 = new JLabel("Pahoillani, tääkin toiminto on aivan keskenräinen");


        add(l1);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Point point;

        Graphics2D g2d = (Graphics2D) g;
        if (ekaKerta) {
            alue = new Rectangle(koko);
            suorakaiteet[0].setLocation(50, 50); 
            suorakaiteet[1].setLocation(100, 100);
            ekaKerta = false;
        }
        point = suorakaiteet[valittuna].getLocation();
        int x = point.x + 5; int y = point.y +15;
        for (int a = 0; a < suorakaiteet.length; a++) 
        {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fill(suorakaiteet[a]);
            g2d.setColor(Color.black);
            g2d.drawString("Esimerkki 123-456", x, y);
        }

        

    }

    boolean checkRect() {
        if (alue == null || valittuna == -1) {
            return false;
        }

        if (alue.contains(suorakaiteet[valittuna].x, suorakaiteet[valittuna].y, suorakaiteet[valittuna].getWidth(), suorakaiteet[valittuna].getHeight())) {
            return true;
        }

        int new_x = suorakaiteet[valittuna].x;
        int new_y = suorakaiteet[valittuna].y;

        if ((suorakaiteet[valittuna].x + suorakaiteet[valittuna].getWidth()) > alue.getWidth()) {
            new_x = (int) alue.getWidth() - (int) (suorakaiteet[valittuna].getWidth() - 1);
        }
        if (suorakaiteet[valittuna].x < 0) {
            new_x = -1;
        }
        if ((suorakaiteet[valittuna].y + suorakaiteet[valittuna].getHeight()) > alue.getHeight()) {
            new_y = (int) alue.getHeight() - (int) (suorakaiteet[valittuna].getHeight() - 1);
        }
        if (suorakaiteet[valittuna].y < 0) {
            new_y = -1;
        }
        suorakaiteet[valittuna].setLocation(new_x, new_y);
        return false;
    }

    private class HiiriAdapteri extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
//            valittuna = -1;
            for (int i = 0; i < suorakaiteet.length; i++) {
                if (e.getSource() == suorakaiteet[i]) {
                    valittuna = i;
                    break;
                } 
            }
            
            if (valittuna == -1) 
                return;

            preX = suorakaiteet[valittuna].x - e.getX();
            preY = suorakaiteet[valittuna].y - e.getY();

            if (suorakaiteet[valittuna].contains(e.getX(), e.getY())) {
                updateLocation(e);
            } else {
                pressOut = true;
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (valittuna == -1) 
                return;
            if (!pressOut) {
                updateLocation(e);
            } else {
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (valittuna == -1) 
                return;
            if (suorakaiteet[valittuna].contains(e.getX(), e.getY())) {
                updateLocation(e);
            } else {
                pressOut = false;
            }
        }

        public void updateLocation(MouseEvent e) {
            if (valittuna == -1) 
                return;
            suorakaiteet[valittuna].setLocation(preX + e.getX(), preY + e.getY());
            checkRect();

            repaint();
        }
    }
}
