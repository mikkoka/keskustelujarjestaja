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
        JMenuBar valikkorivi;
        JMenu tiedostoValikko, submenu, tuomisValikko;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

//Create the menu bar.
        valikkorivi = new JMenuBar();

//Build the first menu.
        tiedostoValikko = new JMenu("Tiedosto");
        tiedostoValikko.setMnemonic(KeyEvent.VK_A);
        tiedostoValikko.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        valikkorivi.add(tiedostoValikko);

//a group of JMenuItems
        menuItem = new JMenuItem("A text-only menu item",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        tiedostoValikko.add(menuItem);

        menuItem = new JMenuItem("Both text and icon",
                new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_B);
        tiedostoValikko.add(menuItem);

        menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_D);
        tiedostoValikko.add(menuItem);

//a group of radio button menu items
        tiedostoValikko.addSeparator();
        ButtonGroup group = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_R);
        group.add(rbMenuItem);
        tiedostoValikko.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Another one");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        tiedostoValikko.add(rbMenuItem);

//a group of check box menu items
        tiedostoValikko.addSeparator();
        cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);
        tiedostoValikko.add(cbMenuItem);

        cbMenuItem = new JCheckBoxMenuItem("Another one");
        cbMenuItem.setMnemonic(KeyEvent.VK_H);
        tiedostoValikko.add(cbMenuItem);

//a submenu
        tiedostoValikko.addSeparator();
        submenu = new JMenu("A submenu");
        submenu.setMnemonic(KeyEvent.VK_S);

        menuItem = new JMenuItem("An item in the submenu");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        submenu.add(menuItem);

        menuItem = new JMenuItem("Another item");
        submenu.add(menuItem);
        tiedostoValikko.add(submenu);

//Build second menu in the menu bar.
        tuomisValikko = new JMenu("Tuo");
        tuomisValikko.setMnemonic(KeyEvent.VK_N);
        tuomisValikko.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        valikkorivi.add(tiedostoValikko);
        valikkorivi.add(tuomisValikko);


        

        JFrame ikkuna = new JFrame("Keskustelujarjestaja");
        ikkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane tp = new JTabbedPane();
        tp.addTab("Järjesteleminen", new JarjestelyPaneeli());
        
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
