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
import java.util.TreeSet;
import javax.swing.*;
import keskjarj.keskjarj.Havainto;
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
    
    private JMenuItem tallennusrivi, latausrivi, elanrivi, havaintorivi, 
            lopetusrivi, VLCrivi, nimeamisrivi, jarjestelyrivi;
    private MenuKuuntelija kuuntelija;
    
    private HakuPaneeli hakupaneeli;
    private JPanel jarjestelypaneeli;
    MedianToistaja toistaja;
    Dimension paneelinKoko;

    Projekti projekti;

    public YlaPaneeli(Dimension paneelinKoko, Projekti projekti) {
        toistaja = new MedianToistaja();
        this.paneelinKoko = paneelinKoko;
        hakupaneeli = new HakuPaneeli(paneelinKoko, projekti);
        jarjestelypaneeli = new JPanel();
        this.projekti = projekti;
        kuuntelija = new MenuKuuntelija();
        valilehdet = new JTabbedPane();
        valilehdet.addTab("Hakeminen", hakupaneeli);
        valilehdet.addTab("Järjesteleminen", jarjestelypaneeli);
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
        
        //Otevalikon rivit

        VLCrivi = new JMenuItem("Toista valittu ote VLC:llä");
        nimeamisrivi = new JMenuItem("Nimeä ote uudelleen");
        jarjestelyrivi = new JMenuItem("Järjestele valittuja otteita");
        
        // Pikanäppäimet
        VLCrivi.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_V, ActionEvent.ALT_MASK));
        nimeamisrivi.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_N, ActionEvent.ALT_MASK));
        
        
        // Tapahtumakuuntelijat
        VLCrivi.addActionListener(kuuntelija);
        nimeamisrivi.addActionListener(kuuntelija);
        jarjestelyrivi.addActionListener(kuuntelija);
        
        // Rivien järjestys valikossa
        otevalikko.add(VLCrivi);
        otevalikko.add(nimeamisrivi);        
        otevalikko.addSeparator();
        otevalikko.add(jarjestelyrivi);
        
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
                if (o == null) {
                    JOptionPane.showMessageDialog(null, "Valitse yksi ote!");
                return;
                }
                toistaja.toista(o);
                
            } else if (e.getSource() == elanrivi) {
                if(tuoAnnotaatioTiedosto()) {
                    if (tuoMediatiedosto())
                        projekti.tuoAnnotaatioita(polku1, new Tallenne(polku2));
                    else projekti.tuoAnnotaatioita(polku1, null);
                    hakupaneeli.paivitaTaulukko();
                    valilehdet.setSelectedIndex(0);
                }
            } else if (e.getSource() == tallennusrivi) {
                System.out.println("tallennushehe");
            } else if (e.getSource() == latausrivi) {
                System.out.println("lataushehe");
            } else if (e.getSource() == lopetusrivi) {
                System.out.println("lopetushehe");
            } else if (e.getSource() == jarjestelyrivi) {
                Ote[] otteet = hakupaneeli.valitutOtteet();
                TreeSet<Ote> ottehet = projekti.getOtteet();
                Havainto havainto1 = projekti.getHavainto(0);
                Havainto havainto2 = projekti.getHavainto(1);
                String[][] tekstit = new String[3][];
                tekstit[0] = new String[2];
                tekstit[0][0] = "ykköseen";
                tekstit[0][1] = "tääki ykköseen!!";
                tekstit[1] = new String[3];
                tekstit[1][0] = "no huh huh";
                tekstit[1][1] = "pellet"; 
                tekstit[1][2] = "ääliöt";
                tekstit[2] = new String[2];
                tekstit[2][0] = "ollaan oikeella";
                tekstit[2][1] = "vasemmisto haisee"; 
                jarjestelypaneeli = new JarjestelyPaneeli(paneelinKoko, tekstit, "tee sitä ja tätä; kyllä se siitä");
                valilehdet.remove(1);
                valilehdet.addTab("Järjesteleminen", jarjestelypaneeli);
                valilehdet.setSelectedIndex(1);
                

                System.out.println("hehehöhö");
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


