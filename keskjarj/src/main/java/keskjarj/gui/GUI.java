package keskjarj.gui;

import java.awt.*;
import java.awt.event.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import keskjarj.keskjarj.*;
import keskjarj.ohjelma.*;

/**
 * Keskustelunjärjestäjän graafinen käyttöliittymä. Luokassa suuri yksityinen
 * sisäluokka MenuKuuntelija, joka toteuttaa kaikki GUIn valikoihin liittyät
 * toiminnallisuudet. Metodit luoProjektivalikko() ja luoOtevalikko() valikoiden
 * luomista varten. Luokalla on paljon yksityisiä kenttiä jotta kommunikaatio
 * luokkien ja metodien välillä onnistuisi.
 *
 * Luokan luomassa ikkunassa on valikko ja kaksi välilehteä. Ensimmäinen
 * välilehti sisältää HakuPaneelin ilmentymän, joka puolestaan sisältää
 * taulukon, jossa projektin otteet ja havainnot näytetään (ja jossa niitä voi
 * järjestellä). Toinen välilehti sisältää aluksi tyhjän JPanelin, mutta
 * valikoista sinne voi luoda JarjestelyPaneelin ilmentymän, jossa järjestellä
 * otteitaan.
 *
 * @author mkahri
 */
public class GUI extends JPanel {

    // Menuihin liittyviä kenttiä
    private JMenuBar valikkorivi;
    private JMenu projektivalikko, otevalikko, tuontivalikko, havaintovalikko;
    private JMenuItem tallennusrivi, latausrivi, elanrivi, havaintorivi,
            lopetusrivi, VLCrivi, nimeamisrivi, jarjestelyrivi, jarjestelynlopetusrivi;
    private MenuKuuntelija kuuntelija;

    // Välilehtiin liittyviä kenttiä
    private JTabbedPane valilehdet;
    private HakuPaneeli hakupaneeli;
    private JPanel jarjestelypaneeli;
    private Dimension paneelinKoko;

    // Keskustelujärjestäjän tietosisältöön ja ohjelmalogiikkaan liittyviä kenttiä
    private MedianToistaja toistaja;
    private Projekti projekti;
    private Havainto jarjesteltava1, jarjesteltava2;
    private JFrame ikkuna;

    /**
     * Konstruktori luo GUIn ikkunoineen, Ikkunaa ei voi sulkea sen nurkan
     * ruksista, kun sitten voisi sattua tilanteita, joissa tallentaminen jää
     * tekemättä ennen ohjelman sulkemista. (Sen sijaan ikkuna on suljettava
     * valikon kautta.)
     *
     * @param projekti
     */
    public GUI(Projekti projekti, Dimension paneelinKoko) {

        this.paneelinKoko = paneelinKoko;
        this.hakupaneeli = new HakuPaneeli(paneelinKoko, projekti);
        this.jarjestelypaneeli = new JPanel();

        this.kuuntelija = new MenuKuuntelija();
        this.valilehdet = new JTabbedPane();
        this.valilehdet.addTab("Hakeminen", hakupaneeli);
        this.valilehdet.addTab("Järjesteleminen", jarjestelypaneeli);

        // Valikoiden luominen
        this.valikkorivi = new JMenuBar();
        this.luoProjektivalikko();
        this.luoOtevalikko();
        this.valikkorivi.add(projektivalikko);
        this.valikkorivi.add(otevalikko);

        // Ikkunan luominen ja asettelu
        ikkuna = new JFrame("Keskustelujärjestäjä");
        ikkuna.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ikkuna.setJMenuBar(valikkorivi);
        ikkuna.getContentPane().add(valilehdet);
        ikkuna.setResizable(true);
        ikkuna.pack();
        ikkuna.setVisible(true);

        // Keskustelunjärjestäjän ohjelmalogiikkaan liittyviä jaettuja resursseja
        this.toistaja = new MedianToistaja();
        this.projekti = projekti;
    }

    private class MenuKuuntelija extends JPanel implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == VLCrivi) {
                valikkoToistaOte();
            } else if (e.getSource() == latausrivi) {
                valikkoLataaTiedostosta();
            } else if (e.getSource() == elanrivi) {
                valikkoTuoAnnotaatioita();
            } else if (e.getSource() == tallennusrivi) {
                valikkoTallennaTiedostoon();
            } else if (e.getSource() == jarjestelynlopetusrivi) {
                valikkoLopetaJarjestely();
            } else if (e.getSource() == lopetusrivi) {
                valikkoSuljeOhjelma();
            } else if (e.getSource() == jarjestelyrivi) {
                valikkoJarjestelePaneelissa();
            } else if (e.getSource() == havaintorivi) {
                valikkoLuoHavaintoKategoria();
            }
        }

        private Path yhteinenValitseTiedosto(String kehotus, int tyyppi, String fil1, String fil2) {

            JFileChooser tiedostonvalitsija = new JFileChooser();
            tiedostonvalitsija.addActionListener(this);
            tiedostonvalitsija.setDialogTitle(kehotus);
            tiedostonvalitsija.setDialogType(tyyppi);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(fil1, fil2);
            tiedostonvalitsija.setFileFilter(filter);
            tiedostonvalitsija.setCurrentDirectory(Paths.get("../aineistoja/").toFile());

            int paluuArvo = tiedostonvalitsija.showOpenDialog(this);
            if (paluuArvo == JFileChooser.APPROVE_OPTION) {
                return tiedostonvalitsija.getSelectedFile().toPath();
            }
            return null;
        }

        private void valikkoTuoAnnotaatioita() {
            Path polku1, polku2;

            polku1 = yhteinenValitseTiedosto("Valitse tuotava tekstitiedosto", JFileChooser.OPEN_DIALOG, "Tektstitiedosto", "txt");
            if (polku1 == null) {
                return;
            }

            polku2 = yhteinenValitseTiedosto("Valitse tuotavaan tekstitiedostoon liittyvä mediatiedosto", JFileChooser.OPEN_DIALOG, "Mediatiedosto", "mp4");
            if (polku2 == null) {
                projekti.tuoAnnotaatioita(polku1, null);
            } else {
                projekti.tuoAnnotaatioita(polku1, new Tallenne(polku2));
            }

            hakupaneeli.hakuMahdollista(true);
            hakupaneeli.paivitaTaulukko();
            valilehdet.setSelectedIndex(0);
        }

        private void valikkoToistaOte() {
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

        private void valikkoLataaTiedostosta() {
            Path temp = yhteinenValitseTiedosto("Ladattava tiedosto", JFileChooser.FILES_ONLY, "Keskustelujärjestäjän tallennus", "keskjarj");
            if (temp != null) {
                projekti = new Projekti();
                hakupaneeli = new HakuPaneeli(paneelinKoko, projekti);
                valilehdet.remove(0);
                valilehdet.addTab("Hakeminen", hakupaneeli);
                ikkuna.getContentPane().add(valilehdet);
                
                Lataaja.lataa(temp, projekti);
                hakupaneeli.paivitaTaulukko();
            }
        }

        private void valikkoTallennaTiedostoon() {
            if (!hakupaneeli.taulukko.getAutoCreateRowSorter()) //tän on tarkoitus olla sen indeksi, että taulukossa dataa
                return;
            Tallentaja tallentaja = new Tallentaja(projekti);
            tallentaja.tallenna(yhteinenValitseTiedosto("Tallennettavan tiedoston nimi?", JFileChooser.SAVE_DIALOG,
                    "Keskustelujärjestäjän tallennus",
                    "keskjarj"));
        }

        private void valikkoLopetaJarjestely() {
            JarjestelyPaneeli jp = (JarjestelyPaneeli) jarjestelypaneeli;
            String[][] tilanne = jp.jarjestelyTilanne();

            for (String s : tilanne[0]) {
                Ote temp = projekti.getProjektinOte(s);
                jarjesteltava1.lisaaOte(temp);
                if (jarjesteltava2 != null) {
                    jarjesteltava2.poistaOte(temp);
                }
            }
            if (jarjesteltava2 != null) {
                for (String s : tilanne[2]) {
                    Ote temp = projekti.getProjektinOte(s);
                    jarjesteltava2.lisaaOte(projekti.getProjektinOte(s));
                    jarjesteltava1.poistaOte(projekti.getProjektinOte(s));

                }
            } else {
                for (String s : tilanne[2]) {
                    Ote temp = projekti.getProjektinOte(s);
                    jarjesteltava1.poistaOte(projekti.getProjektinOte(s)); // jos j2 oli null, täytyy silti poistaa j1:stä oikeassa reunassa olleet
                }
            }

            for (String s : tilanne[1]) {
                Ote temp = projekti.getProjektinOte(s);
                jarjesteltava1.poistaOte(projekti.getProjektinOte(s));
                if (jarjesteltava2 != null) {
                    jarjesteltava2.poistaOte(projekti.getProjektinOte(s));
                }
            }

            hakupaneeli.paivitaTaulukko();
            jarjestelynlopetusrivi.setEnabled(false);
            jarjestelyrivi.setEnabled(true);
            jarjestelypaneeli = new JPanel();
            valilehdet.remove(1);
            valilehdet.addTab("Järjesteleminen", jarjestelypaneeli);
            valilehdet.setSelectedIndex(0);
            jarjesteltava1 = jarjesteltava2 = null;

        }

        private void valikkoSuljeOhjelma() {
            Object[] options = {"Kyllä",
                "Ei kiitos",
                "Peruuta"};
            int lopetus = JOptionPane.showOptionDialog(jarjestelypaneeli,
                    "Haluatko tallentaa?",
                    "Lopettaminen",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]);
            if (lopetus == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (lopetus == JOptionPane.YES_OPTION) {
                    this.valikkoTallennaTiedostoon();
                }
            System.exit(0);
            } 

        private void valikkoJarjestelePaneelissa() {
            int[] valittujenIndeksit = hakupaneeli.valitutOtteet();           
            if (jarjestelyEiValittuja(valittujenIndeksit))
                return;

            if(!jarjestelyKategorioidenValitseminen())
                return;
            
            String[][] tekstit = jarjestelyLuoSarakkeet(projekti.getOtteet(valittujenIndeksit));             
            if (jarjestelyLiikaaJarjesteltavia(tekstit))
                return;
            
            String ohje = jarjestelyOhje();
            
            jarjestelypaneeli = new JarjestelyPaneeli(paneelinKoko, tekstit, ohje, projekti);
            valilehdet.remove(1);
            valilehdet.addTab("Järjesteleminen", jarjestelypaneeli);
            valilehdet.setSelectedIndex(1);
            jarjestelynlopetusrivi.setEnabled(true);
            jarjestelyrivi.setEnabled(false);
        }

        
        private boolean jarjestelyLiikaaJarjesteltavia (String[][] tekstit)
        {     
            for (int i = 0; i < 3; i++) {
                if (tekstit[i].length > 29) {
                    JOptionPane.showMessageDialog(null, "Liikaa otteita!\n(Max 29 yhteen kategoriaan.)");
                    return true;
                }
            }
            return false;
        }
        
        private boolean jarjestelyEiValittuja(int[] valittujenIndeksit) {
            if (valittujenIndeksit.length == 0) {
                JOptionPane.showMessageDialog(null, "Valitse otteita!");
                return true;
            }
            return false;
        }
        
        private boolean jarjestelyKategorioidenValitseminen() {

            String valittuKategoria1 = jarjestelyValitseKategoria("Valitse ainakin yksi havaintokategoria");
            if (valittuKategoria1 == null) {
                return false;
            } else {
                jarjesteltava1 = projekti.getHavainto(valittuKategoria1);
            }

            String valittuKategoria2 = jarjestelyValitseKategoria("Voit valita toisen havaintokategorian");
            jarjesteltava2 = projekti.getHavainto(valittuKategoria2);

            if (valittuKategoria1.equals(valittuKategoria2)) {
                JOptionPane.showMessageDialog(null, "Valitsit saman kategorian kahteen kertaan!");
                return false;
            }

            if (jarjesteltava1.sisaltaaSamojaOtteita(jarjesteltava2)) {
                JOptionPane.showMessageDialog(null, "\"" + jarjesteltava2.getNimi() + "\" sisältää samoja otteita kuin \"" + jarjesteltava1.getNimi() + "\"");
                return false;
            }
            return true;
        }
        
        private String jarjestelyOhje() {
            String ohje = "\"" + jarjesteltava1.getNimi() + "\" vasemmalle";
            if (jarjesteltava2 != null)
                ohje = ohje + ", \"" + jarjesteltava2.getNimi() + "\" oikealle.";
            return ohje;   
        }
        
        private String jarjestelyValitseKategoria(String kehotus) {
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
        
        private String[][] jarjestelyLuoSarakkeet(Ote[] valitutOtteet)
        {
            ArrayList<String> temp1 = new ArrayList();
            ArrayList<String> temp2 = new ArrayList();
            ArrayList<String> temp3 = new ArrayList();

            for (Ote o : valitutOtteet) {
                if (jarjesteltava1.sisaltaa(o)) {
                    temp1.add(o.getTunnus());
                } else if (jarjesteltava2 != null) {
                    if (jarjesteltava2.sisaltaa(o)) {
                        temp3.add(o.getTunnus());
                    } else {
                        temp2.add(o.getTunnus());
                    }
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
            return tekstit;
        }

        private void valikkoLuoHavaintoKategoria() {
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

    private void luoOtevalikko() {
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

    private void luoProjektivalikko() {

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
}
