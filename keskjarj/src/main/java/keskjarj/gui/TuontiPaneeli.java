/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.*;
import javax.swing.*;
import keskjarj.keskjarj.Projekti;

/**
 *
 * @author mkahri
 */
public class TuontiPaneeli extends JPanel implements ActionListener {
    JButton tekstitiedostonappi, mediatiedostonappi, tuontinappi;
    JTextField tekstikentta1, tekstikentta2;
    JFileChooser tiedostonvalitsija;

    Projekti projekti;
 
    public TuontiPaneeli(Projekti projekti) {
        this.setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
        this.projekti = projekti;

        tekstitiedostonappi = new JButton("Valitse annotaatioita sisältävä tekstitiedosto...");
        tekstitiedostonappi.addActionListener(this);

        mediatiedostonappi = new JButton("Valitse mediatiedosto...");
        mediatiedostonappi.addActionListener(this);
        
        tuontinappi = new JButton("Tuo...");
        tuontinappi.addActionListener(this);
 
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(tekstitiedostonappi);
        buttonPanel.add(mediatiedostonappi);
        
        tekstikentta1 = new JTextField("Annotaatioita sisältävän tekstitiedoston nimi: Puuttuu", 50);
        tekstikentta2 = new JTextField("Mediatiedoston nimi: Puuttuu", 50);

        //Add the buttons and the log to this panel.
        add(buttonPanel);
        //add(tuontinappi, BorderLayout.SOUTH);
        add(tekstikentta1);
        add(tekstikentta2);
        add(tuontinappi);
        
        //this.setPreferredSize(new Dimension (800,200));
    }
 
        @Override
        public void actionPerformed(ActionEvent e) {
            Path polku;
            
            if (e.getSource() == tekstitiedostonappi) {
                tiedostonvalitsija = new JFileChooser();
                tiedostonvalitsija.addActionListener(this);
                //tiedostonvalitsija.setMultiSelectionEnabled(true);
                tiedostonvalitsija.setDialogTitle("Valitse tuotava tekstitiedosto");
                tiedostonvalitsija.setCurrentDirectory(Paths.get("../aineistoja/").toFile());
                int paluuArvo = tiedostonvalitsija.showOpenDialog(this);
                if (paluuArvo == JFileChooser.APPROVE_OPTION) {
                    File file = tiedostonvalitsija.getSelectedFile();
                    polku = file.toPath();
                    tekstikentta1.setText(polku.toString());
                    projekti.tuoAnnotaatioita(polku);
                    }
            } else if (e.getSource() == tuontinappi) {
                System.out.println("tallennushehe");
            } else if (e.getSource() == mediatiedostonappi) {
                System.out.println("lataushehe");
            } 
        }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Annotaatioiden tuonti");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Projekti projekti = new Projekti();
        frame.add(new TuontiPaneeli(projekti));
 
        frame.pack();
        frame.setVisible(true);
    }

 
    public static void main(String[] args) {
        Projekti projekti = new Projekti();
        TuontiPaneeli tp = new TuontiPaneeli(projekti);
        createAndShowGUI();
    }
}