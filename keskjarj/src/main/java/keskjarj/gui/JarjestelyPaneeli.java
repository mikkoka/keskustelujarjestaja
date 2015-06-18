/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.gui;

import keskjarj.tieto.Projekti;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.*;
import keskjarj.ohjelma.MedianToistaja;

/**
 * Luokka luo paneelin, jolla voi  hiirellä vetää suorakaiteita,
 * jotka edustavat keskusteluotteita, ikkunan eri osiin. Ikkunan eri osat 
 * puolestaan edustavat eri havaintokategorioita.
 * Malli, josta luokkaa lähdettiin muokkaamaan, täältä:
 * http://stackoverflow.com/questions/13220971/how-to-drag-object
 * @author mkahri
 */
public class JarjestelyPaneeli extends JPanel {

    private SuoraKaide[] suorakaiteet;
    private int preX, preY;
    private boolean ekaKerta = true;
    private Rectangle alue;
    private boolean pressOut;
    private Dimension koko;
    private int val = -1;
    private JLabel l1;
    private String[][] tekstit;
    private Line2D viiva1, viiva2;
    private MedianToistaja toistaja;
    private Projekti projekti;

    public JarjestelyPaneeli(Dimension koko, String[][] tekstit, String ohje, Projekti projekti) 
    {
        this.projekti = projekti;
        toistaja = new MedianToistaja();
        this.pressOut = false;
        this.koko = koko;
        int lsk = 0;
        for(int i = 0; i < 3; i++)
            lsk += tekstit[i].length;        
        this.suorakaiteet = new SuoraKaide[lsk];
        this.tekstit = tekstit;
        //this.setFont(new Font(this.getFont().getName(), 16, 16));
        
        int count = 0;
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < tekstit[a].length; b++) {
                suorakaiteet[count] = new SuoraKaide(0, 0, 250, 22, tekstit[a][b]);
                count++;
            }
        }
        viiva1 = new Line2D.Double(koko.getWidth() / 3 - 20, 
                20, 
                koko.getWidth() / 3 - 20, 
                koko.getHeight() - 10);
        
        viiva2 = new Line2D.Double(2 * koko.getWidth() / 3 + 20, 
                20, 
                2 * koko.getWidth() / 3 + 20, 
                koko.getHeight() - 10);
        
        addMouseMotionListener(new HiiriAdapteri());
        addMouseListener(new HiiriAdapteri());

        setPreferredSize(koko);
        l1 = new JLabel(ohje);

        add(l1);
    }
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Point point;

        Graphics2D g2d = (Graphics2D) g;
        if (ekaKerta) {
            alue = new Rectangle(koko);
            int vert;
            int hor[] = new int[3];
            hor[0] = 20;
            hor[1] = (int)alue.getCenterX() - (int)suorakaiteet[0].getWidth()/2;
            hor[2] = (int)viiva2.getX1() + 20;
            int laskuri = 0;
            for (int a = 0; a < 3; a++) {
                vert = 50;
                for (int b = 0; b < tekstit[a].length; b++) {
                    suorakaiteet[laskuri].setLocation(hor[a], vert);
                    laskuri++;
                    vert += 25;
                }
            }            
            ekaKerta = false;
        }
        
        int laskuri = 0;
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < tekstit[a].length; b++) {
                point = suorakaiteet[laskuri].getLocation();
                int x = point.x + 5;
                int y = point.y + 15;
                g2d.setColor(Color.LIGHT_GRAY);
                if (val == laskuri)
                    g2d.fill(suorakaiteet[laskuri]);
                else
                    g2d.draw(suorakaiteet[laskuri]);
                g2d.setColor(Color.black);
                g2d.drawString(tekstit[a][b], x, y);
                laskuri++;
            }
        }

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.draw(viiva1);
        g2d.draw(viiva2);
        g2d.setColor(Color.black);
        g2d.drawString("AltGR -klikkaus toistaa videon", 
                (int)koko.getWidth()/2 - 70, 
                (int)koko.getHeight() - 5);
    }

    boolean checkRect() {
        if (alue == null || val == -1) {
            return false;
        }

        if (alue.contains(suorakaiteet[val].x, suorakaiteet[val].y, suorakaiteet[val].getWidth(), suorakaiteet[val].getHeight())) {
            return true;
        }

        int new_x = suorakaiteet[val].x;
        int new_y = suorakaiteet[val].y;

        if ((suorakaiteet[val].x + suorakaiteet[val].getWidth()) > alue.getWidth()) {
            new_x = (int) alue.getWidth() - (int) (suorakaiteet[val].getWidth() - 1);
        }
        if (suorakaiteet[val].x < 0) {
            new_x = -1;
        }
        if ((suorakaiteet[val].y + suorakaiteet[val].getHeight()) > alue.getHeight()) {
            new_y = (int) alue.getHeight() - (int) (suorakaiteet[val].getHeight() - 1);
        }
        if (suorakaiteet[val].y < 0) {
            new_y = -1;
        }
        suorakaiteet[val].setLocation(new_x, new_y);
        return false;
    }
    
    /**
     * Luokasta GUI käsin käytetään tätä metodia, kun käyttäjä on valikossa ilmaissut
     * haluavansa katsoa otteeseen liittyvän videon. Kentän val kokonaisluku kertoo
     * valittuna olevan suorakaiteen
     */
    public void toistaTallenneValikosta() {
        if (val > -1) {
            toistaTallenne(val);
        }
    }

    /**
     * 
     * @param valittu
     */
    public void toistaTallenne(int valittu) {
        String nimi = suorakaiteet[valittu].tunnus;
        toistaja.toista(projekti.getProjektinOte(nimi));
    }
    
    /**
     * Luokasta GUI käsin käytetään tätä metodia, kun halutaan tietää, miten 
     * otteita edustavat suorakaiteet ovat järjestettyinä ikkunassa. Palauttaa kolmirivisen
     * taulukon, jonka riveillä tieto siitä, ovatko otteet ruutua jakavien viivojen
     * vasemmalla tai oikealla puolella, tai niitten välissä
     * @return
     */
    public String[][] jarjestelyTilanne () {
        ArrayList<String> temp1 = new ArrayList();
        ArrayList<String> temp2 = new ArrayList();
        ArrayList<String> temp3 = new ArrayList();

        for (SuoraKaide r : suorakaiteet)
        {
            if (r.getCenterX() < viiva1.getX1())
                temp1.add(r.tunnus);
            else if (r.getCenterX() > viiva2.getX1())
                temp3.add(r.tunnus);
            else temp2.add(r.tunnus);
        }
        String[][] palautus = new String[3][];
        
        palautus[0] = new String[temp1.size()];
        for (int a = 0; a < temp1.size(); a++) {
             palautus[0][a] = temp1.get(a);
        }
        
        palautus[1] = new String[temp2.size()];
        for (int a = 0; a < temp2.size(); a++) {
             palautus[1][a] = temp2.get(a);
        }
        
        palautus[2] = new String[temp3.size()];
        for (int a = 0; a < temp3.size(); a++) {
             palautus[2][a] = temp3.get(a);
        }
        
 
        return palautus;
    }
    
    private class SuoraKaide extends Rectangle {
        String tunnus;
        public SuoraKaide(int x, int y, int width, int height, String tunnus) {
            super(x, y, width, height);
            this.tunnus = tunnus;
        }
    }
    
    private class HiiriAdapteri extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            for (int i =0; i < suorakaiteet.length; i++) {
                if (suorakaiteet[i].contains(e.getPoint())) {
                    val = i;
                    break;
                } 
            }
            
            if (val == -1) 
                return;
            if (e.isAltGraphDown())
                toistaTallenne(val);

            preX = suorakaiteet[val].x - e.getX();
            preY = suorakaiteet[val].y - e.getY();

            if (suorakaiteet[val].contains(e.getX(), e.getY())) {
                updateLocation(e);
            } else {
                pressOut = true;
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (val == -1) 
                return;
            if (!pressOut) {
                updateLocation(e);
            } else {
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (val == -1) 
                return;
            if (suorakaiteet[val].contains(e.getX(), e.getY())) {
                updateLocation(e);
            } else {
                pressOut = false;
            }
        }

        public void updateLocation(MouseEvent e) {
            if (val == -1) 
                return;
            suorakaiteet[val].setLocation(preX + e.getX(), preY + e.getY());
            checkRect();

            repaint();
        }    
    }    
}
