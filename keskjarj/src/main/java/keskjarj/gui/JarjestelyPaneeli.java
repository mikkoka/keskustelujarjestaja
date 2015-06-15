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
    boolean pressOut;
    private Dimension koko;
    int valittuna = -1;
    JLabel l1;
    String[] tekstit;

    public JarjestelyPaneeli(Dimension koko) 
    {
        this.pressOut = false;
        this.koko = koko;
        this.suorakaiteet = new Rectangle[2];
        this.tekstit = new String[2];
        suorakaiteet[0] = new Rectangle(0, 0, 200, 22);
        suorakaiteet[1] = new Rectangle(50, 50, 200, 22);
        tekstit[0] = "hehe";
        tekstit[1] = "hoho";

//        setBackground(Color.lightGray);
        addMouseMotionListener(new HiiriAdapteri());
        addMouseListener(new HiiriAdapteri());

        setPreferredSize(koko);

        l1 = new JLabel("Pahoillani, tääkin toiminto on aivan keskenräinen");


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

        for (int i =0; i < suorakaiteet.length; i++) {
            point = suorakaiteet[i].getLocation();
            int x = point.x + 5; int y = point.y +15;
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.draw(suorakaiteet[i]);
            g2d.setColor(Color.black);
            g2d.drawString(tekstit[i], x, y);
            
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
            System.out.println(e.paramString());
            System.out.println(e.isAltGraphDown());//suorakaiteet[0].equals(e.getSource()));
            System.out.println(e.getButton());
            for (int i =0; i < suorakaiteet.length; i++) {
                if (suorakaiteet[i].contains(e.getPoint())) {
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
