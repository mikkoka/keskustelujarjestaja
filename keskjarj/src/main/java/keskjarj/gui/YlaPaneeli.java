/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import keskjarj.keskjarj.Ote;
import keskjarj.keskjarj.Projekti;
import keskjarj.keskjarj.Tallenne;
import keskjarj.ohjelma.MedianToistaja;

/**
 *
 * @author mkahri
 */
public class YlaPaneeli extends JPanel {

    private JTabbedPane valilehdet;
    private JMenuBar valikkorivi;
    private JMenu projektivalikko, otevalikko, tuontivalikko, havaintovalikko;
    private JMenuItem tallennusrivi, latausrivi, elanrivi, havaintorivi, lopetusrivi, VLCrivi, nimeamisrivi;
    private MenuKuuntelija kuuntelija;
    private HakuPaneeli hakupaneeli;

    Projekti projekti;

    public YlaPaneeli(Dimension paneelinKoko, Projekti projekti) {
        hakupaneeli = new HakuPaneeli(paneelinKoko, projekti);
        this.projekti = projekti;
        kuuntelija = new MenuKuuntelija();
        valilehdet = new JTabbedPane();
        valilehdet.addTab("Hakeminen", hakupaneeli);
        valilehdet.addTab("Järjesteleminen", new JarjestelyPaneeli(paneelinKoko));
        valikkorivi = new JMenuBar();
        
        luoProjektivalikko();
        luoOtevalikko();

        valikkorivi.add(projektivalikko);
        valikkorivi.add(otevalikko);
        
        this.setLayout(new BorderLayout());
        this.add(valilehdet, BorderLayout.SOUTH);
        this.add(valikkorivi, BorderLayout.NORTH);
    }
    
    private void luoOtevalikko () {
        otevalikko = new JMenu("Otteet");
        otevalikko.setMnemonic(KeyEvent.VK_O);

        VLCrivi = new JMenuItem("Toista valittu ote VLC:llä");
        nimeamisrivi = new JMenuItem("Nimeä ote uudelleen");
        
        VLCrivi.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_V, ActionEvent.ALT_MASK));
        nimeamisrivi.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_N, ActionEvent.ALT_MASK));
        
        // Tapahtumakuuntelijat
        VLCrivi.addActionListener(kuuntelija);
        nimeamisrivi.addActionListener(kuuntelija);
        
        // Rivien järjestys valikossa
        otevalikko.add(VLCrivi);
        otevalikko.addSeparator();
        otevalikko.add(nimeamisrivi);
        
    }
    
    private void luoProjektivalikko () {
        
        projektivalikko = new JMenu("Projekti");
        projektivalikko.setMnemonic(KeyEvent.VK_P);

        // Valikkoon tulevat rivit
        tallennusrivi = new JMenuItem("Tallenna", KeyEvent.VK_T);
        latausrivi = new JMenuItem("Lataa", KeyEvent.VK_L);
        elanrivi = new JMenuItem("Elanista");
        havaintorivi = new JMenuItem("Otetta koskeva");
        lopetusrivi = new JMenuItem("Lopeta");
        
        // Alivalikko tuonnille
        tuontivalikko = new JMenu("Tuo");
        tuontivalikko.add(elanrivi);
        
        // Alivalikko havaintokategorioitten luonnille
        havaintovalikko = new JMenu("Luo havaintokategoria");
        havaintovalikko.add(havaintorivi);
        
        // Tapahtumakuuntelijat valikon riveille
        tallennusrivi.addActionListener(kuuntelija);
        latausrivi.addActionListener(kuuntelija);
        elanrivi.addActionListener(kuuntelija);
        havaintorivi.addActionListener(kuuntelija);
        lopetusrivi.addActionListener(kuuntelija);
        
        // Rivien järjestys valikossa
        projektivalikko.add(tallennusrivi);
        projektivalikko.add(latausrivi);
        projektivalikko.addSeparator();
        projektivalikko.add(tuontivalikko);
        projektivalikko.addSeparator();
        projektivalikko.add(havaintovalikko);
        projektivalikko.addSeparator();
        projektivalikko.add(lopetusrivi);
    }

    private class MenuKuuntelija extends JPanel implements ActionListener {
        JFileChooser tiedostonvalitsija;
        Path polku1, polku2;

        @Override
        public void actionPerformed(ActionEvent e) {

            
            if (e.getSource() == VLCrivi) {
                Ote o = hakupaneeli.valittuOte();
                MedianToistaja mt = new MedianToistaja();
                System.out.println(o.getTunnus());
                mt.toista(o);
                
            } else if (e.getSource() == elanrivi) {
                if(tuoAnnotaatioTiedosto()) {
                    if (tuoMediatiedosto())
                        projekti.tuoAnnotaatioita(polku1, new Tallenne(polku2));
                    else projekti.tuoAnnotaatioita(polku1, null);
                    hakupaneeli.paivitaTaulukko();
                }
            } else if (e.getSource() == tallennusrivi) {
                System.out.println("tallennushehe");
            } else if (e.getSource() == latausrivi) {
                System.out.println("lataushehe");
            } else if (e.getSource() == lopetusrivi) {
                System.out.println("lopetushehe");
            } else if (e.getSource() == nimeamisrivi) {
                System.out.println("nimeämishehe");
            } else if (e.getSource() == havaintorivi) {
                String s = (String)JOptionPane.showInputDialog("Anna havaintokategotian nimi", "nimi");
                if (s == null)
                    return;     
                if (!projekti.luoHaivaintokategoria(s))
                    JOptionPane.showMessageDialog(null, "Kategorian luonti epäonnistui");
                else { hakupaneeli.paivitaTaulukko();
                }
            } 
        }
        
        private boolean tuoAnnotaatioTiedosto() {
            tiedostonvalitsija = new JFileChooser();
            tiedostonvalitsija.addActionListener(this);
            //tiedostonvalitsija.setMultiSelectionEnabled(true);
            tiedostonvalitsija.setDialogTitle("Valitse tuotava tekstitiedosto");
            tiedostonvalitsija.setCurrentDirectory(Paths.get("../aineistoja/").toFile());
            int paluuArvo = tiedostonvalitsija.showOpenDialog(this);
            if (paluuArvo == JFileChooser.APPROVE_OPTION) {
                File file = tiedostonvalitsija.getSelectedFile();
                polku1 = file.toPath();
                return true;
            } else {
                return false;
            }
        }

        // copy-paste -koodia, joo joo. Korjataan.

        private boolean tuoMediatiedosto() {
            tiedostonvalitsija = new JFileChooser();
            tiedostonvalitsija.addActionListener(this);
            //tiedostonvalitsija.setMultiSelectionEnabled(true);
            tiedostonvalitsija.setDialogTitle("Valitse tuotavaan tekstitiedostoon liittyvä mediatiedosto");
            tiedostonvalitsija.setCurrentDirectory(Paths.get("../aineistoja/").toFile());
            int paluuArvo = tiedostonvalitsija.showOpenDialog(this);
            if (paluuArvo == JFileChooser.APPROVE_OPTION) {
                File file = tiedostonvalitsija.getSelectedFile();
                polku2 = file.toPath();
                return true;
            } else {
                return false;
            }
        }

    }
}


