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
import java.util.ArrayList;
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
            lopetusrivi, VLCrivi, nimeamisrivi, jarjestelyrivi, jarjestelynlopetusrivi;
    private MenuKuuntelija kuuntelija;
    
    private HakuPaneeli hakupaneeli;
    private JPanel jarjestelypaneeli;
    private MedianToistaja toistaja;
    private Dimension paneelinKoko;

    private Projekti projekti;
    
    private Havainto jarjesteltava1, jarjesteltava2;

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
        jarjestelynlopetusrivi = new JMenuItem("Lopeta järjestely ja tuo otteet taulukkoon");
        jarjestelynlopetusrivi.setEnabled(false);
        
        // Pikanäppäimet
        VLCrivi.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_V, ActionEvent.ALT_MASK));
        nimeamisrivi.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_N, ActionEvent.ALT_MASK));
        
        
        // Tapahtumakuuntelijat
        VLCrivi.addActionListener(kuuntelija);
        nimeamisrivi.addActionListener(kuuntelija);
        jarjestelyrivi.addActionListener(kuuntelija);
        jarjestelynlopetusrivi.addActionListener(kuuntelija);
        
        // Rivien järjestys valikossa
        otevalikko.add(VLCrivi);
        otevalikko.add(nimeamisrivi);        
        otevalikko.addSeparator();
        otevalikko.add(jarjestelyrivi);
        otevalikko.add(jarjestelynlopetusrivi);
        
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
                toistaOte();

                
                

            } else if (e.getSource() == elanrivi) {
                if (tuoAnnotaatioTiedosto()) {
                    if (tuoMediatiedosto()) {
                        projekti.tuoAnnotaatioita(polku1, new Tallenne(polku2));
                    } else {
                        projekti.tuoAnnotaatioita(polku1, null);
                    }
                    hakupaneeli.paivitaTaulukko();
                    valilehdet.setSelectedIndex(0);
                }
            } else if (e.getSource() == tallennusrivi) {
                System.out.println("tallennushehe");
            } else if (e.getSource() == jarjestelynlopetusrivi) {
                JarjestelyPaneeli jp = (JarjestelyPaneeli) jarjestelypaneeli;
                String[][] tilanne = jp.jarjestelyTilanne();
                for (String s : tilanne[0]) {
                    jarjesteltava1.lisaaOte(projekti.getOte(s));
                    System.out.println(s);
                }
                System.out.println("\nhehe\n");
                if (jarjesteltava2 != null) {
                for (String s : tilanne[1]) {
                    jarjesteltava2.lisaaOte(projekti.getOte(s));
                    System.out.println(s);
                    
                }
                }
                    hakupaneeli.paivitaTaulukko();
                    valilehdet.setSelectedIndex(0);                    
                    
                
                
            } else if (e.getSource() == lopetusrivi) {
                Object[] options = {"Kyllä",
                    "Ei kiitos",
                    "Peruuttaminen"};
                int lopetus = JOptionPane.showOptionDialog(jarjestelypaneeli,
                        "Haluaisitko tallentaa?",
                        "Lopettaminen",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);
                System.out.println(lopetus);
                //int lopetus = JOptionPane.showConfirmDialog(jarjestelypaneeli, options);
                if (lopetus == 2) {
                    return;
                } else if (lopetus == 1) {
                    System.exit(0);
                }
            } else if (e.getSource() == jarjestelyrivi) {

                String ohje = "";
                boolean toinenPuuttuu = false;

                int[] valittujenIndeksit = hakupaneeli.valitutOtteet();
                if (valittujenIndeksit.length == 0) {
                    JOptionPane.showMessageDialog(null, "Valitse otteita!");
                    return;
                }
                Ote[] valitutOtteet = projekti.getOtteet(valittujenIndeksit);

                String s1 = valitseKategoria("Valitse ainakin yksi havaintokategoria");//(String) JOptionPane.showInputDialog("Anna 1. havaintokategorian nimi", "nimi");

                if (s1 == null) {
                    return;
                } else {
                    jarjesteltava1 = projekti.getHavainto(s1);
                    ohje = "\"" + jarjesteltava1.getNimi() + "\" vasemmalle";
                }

                String s2 = valitseKategoria("Toinen havaintokategoria");//(String) JOptionPane.showInputDialog("Anna 2. havaintokategorian nimi", "nimi");

                if (s2 == null)
                    toinenPuuttuu = true;
                else if (s2.equals(s1)) {
                    JOptionPane.showMessageDialog(null, "Valitsit saman kategorian kahteen kertaan!");
                    return;
                } 
                
                if (!toinenPuuttuu)
                {
                    jarjesteltava2 = projekti.getHavainto(s2);


                if (jarjesteltava2.sisaltaaSamojaOtteita(jarjesteltava1)) {
                    JOptionPane.showMessageDialog(null, "\"" + jarjesteltava2.getNimi() + "\" sisältää samoja otteita kuin \"" + jarjesteltava1.getNimi() + "\"");
                    return;
                }
                ohje = ohje + ", \"" + jarjesteltava2.getNimi() + "\" oikealle.";
                
                } else jarjesteltava2 = null;

                ArrayList<String> temp1 = new ArrayList();
                ArrayList<String> temp2 = new ArrayList();
                ArrayList<String> temp3 = new ArrayList();

                for (Ote o : valitutOtteet) {
                    if (jarjesteltava1.sisaltaa(o)) {
                        temp1.add(o.getTunnus());
                    } else if (jarjesteltava2 != null) {
                        if (jarjesteltava2.sisaltaa(o)) {
                            temp3.add(o.getTunnus());
                        } else temp2.add(o.getTunnus());
                    } else {
                        temp2.add(o.getTunnus());
                    }
                }
                
                String[][] tekstit = new String[3][];
                tekstit[0] = new String[temp1.size()];

                for (int a = 0; a < temp1.size(); a++) {
                    tekstit[0][a] = temp1.get(a);
                }

                tekstit[1] = new String[temp2.size()];
                for (int a = 0; a < temp2.size(); a++) {
                    tekstit[1][a] = temp2.get(a);
                }

                tekstit[2] = new String[temp3.size()];
                for (int a = 0; a < temp3.size(); a++) {
                    tekstit[2][a] = temp3.get(a);
                }
                
                // tarkistetaan (tälleen tosi kömpelösti), ettei liikaa otteita
                for (int i = 0; i < 3; i++)
                     if (tekstit[i].length > 29)
                         return;
         

                jarjestelypaneeli = new JarjestelyPaneeli(paneelinKoko, tekstit, ohje, projekti);
                valilehdet.remove(1);
                valilehdet.addTab("Järjesteleminen", jarjestelypaneeli);
                valilehdet.setSelectedIndex(1);
                jarjestelynlopetusrivi.setEnabled(true);
                jarjestelyrivi.setEnabled(false);

            } else if (e.getSource() == havaintorivi) {
                String s = (String) JOptionPane.showInputDialog("Anna havaintokategotian nimi", "nimi");
                if (s == null) {
                    return;
                }
                if (!projekti.luoHaivaintokategoria(s)) {
                    JOptionPane.showMessageDialog(null, "Kategorian luonti epäonnistui");
                } else {
                    hakupaneeli.paivitaTaulukko();
                    valilehdet.setSelectedIndex(0);
                }
            }
        }

        private String valitseKategoria(String kehotus) {
            Object[] possibilities = projekti.getHavainnotString();
            return (String) JOptionPane.showInputDialog(
                    jarjestelypaneeli,
                    kehotus,
                    "Järjesteltävät kategoriat",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    null);
        }

        private void toistaOte() {
            if (valilehdet.getSelectedIndex() == 1) {
                try {
                    JarjestelyPaneeli jp = (JarjestelyPaneeli) jarjestelypaneeli;
                    jp.toistaTallenneValikosta();
                } catch (Exception eionnistunut) {
                }
            } else if (valilehdet.getSelectedIndex() == 0) {
                Ote o = hakupaneeli.valittuOte();
                if (o == null) {
                    JOptionPane.showMessageDialog(null, "Valitse yksi ote!");
                    return;
                }
                toistaja.toista(o);
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
            hakupaneeli.hakuMahdollista();
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


