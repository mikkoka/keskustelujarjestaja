/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package keskjarj.keskjarj;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import javax.swing.*;
import keskjarj.gui.*;

/**
 * Tämä on Keskustelunjärjestäjän pääluokka, josta ohjelman suoritus alkaa.
 * Tarkoitus on, että täältä lähinnä käynnistetään GUI.
 * @author mkahri
 */
public class Keskustelujarjestaja {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        JFrame ikkuna = new JFrame("Keskustelujarjestaja");
        ikkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane tp = new JTabbedPane(); 
        tp.addTab("Hakeminen", new JarjestelyPaneeli());
        tp.addTab("Järjesteleminen", new JarjestelyPaneeli());

        JMenuBar valikkorivi = new JMenuBar();

        JMenu projektiValikko = new JMenu("Projekti");
        projektiValikko.setMnemonic(KeyEvent.VK_P);
        valikkorivi.add(projektiValikko);

        JMenuItem tallennusNappi = new JMenuItem("Tallenna", KeyEvent.VK_T);
        projektiValikko.add(tallennusNappi);
        
        JMenuItem latausNappi = new JMenuItem("Lataa", KeyEvent.VK_L);
        projektiValikko.add(latausNappi);
        
        projektiValikko.addSeparator();
        JMenu alivalikko = new JMenu("Tuo");
        projektiValikko.add(alivalikko);

        JMenuItem elanNappi = new JMenuItem("Elanista");
        alivalikko.add(elanNappi);
        
        projektiValikko.addSeparator();
        JMenuItem lopetusNappi = new JMenuItem("Lopeta");
        projektiValikko.add(lopetusNappi);

        JMenu oteValikko = new JMenu("Ote");
        
        JMenuItem VLCNappi = new JMenuItem("Toista VLC:llä");
        VLCNappi.setMnemonic(KeyEvent.VK_V);
        oteValikko.add(VLCNappi);
        
        oteValikko.addSeparator();
        
        JMenuItem nimiNappi = new JMenuItem("Nimeä uudelleen");
        VLCNappi.setMnemonic(KeyEvent.VK_N);
        oteValikko.add(nimiNappi);

        valikkorivi.add(projektiValikko);
        valikkorivi.add(oteValikko);
        
        ikkuna.getContentPane().add(tp);
        ikkuna.setJMenuBar(valikkorivi);
        
        ikkuna.pack();
        ikkuna.setVisible(true);
//        Projekti projekti = new Projekti();
//        
//        Path polku1 = Paths.get("../aineistoja/ElanExample.txt");
//        Path polku2 = Paths.get("../aineistoja/ElanExample2.txt");
//        Path polku3 = Paths.get("../aineistoja/ElanExample3.txt"); //sis. 2 uutta riviä edell. verrattuna
//        Path polku4 = Paths.get("../aineistoja/ElanExample4.txt");
//        
//        projekti.tuoAnnotaatioita(polku1);
//        projekti.tuoAnnotaatioita(polku2);
//        projekti.tuoAnnotaatioita(polku3);
//        projekti.tuoAnnotaatioita(polku4);
//        
//        HashSet<Havainto> kokeilu = projekti.havainnot();
//        
//        System.out.println("\nOtteet havaintokategorioittain:");
//        for (Havainto h : kokeilu)
//        {
//            System.out.println(h.nimi);
//            for (Ote o : h.otteet)
//                System.out.println(o.getTunnus());
//        }
//        
//        System.out.println("\nHavaintokategoriaan \"hehe\" liittyvät otteet:");
//        HashSet<Ote> kokeilu3 = projekti.otteet("hehe");
//        for (Ote o : kokeilu3)
//            System.out.println(o.getTunnus());
//        
//        System.out.println("\nHavaintokategoriaan \"Trenscraption\" liittyvät otteet:");
//        HashSet<Ote> kokeilu2 = projekti.otteet("Trenscraption");
//        for (Ote o : kokeilu2)
//            System.out.println(o.getTunnus());
        
        
    }
}
