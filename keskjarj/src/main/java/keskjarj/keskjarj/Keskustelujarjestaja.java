/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package keskjarj.keskjarj;

import java.awt.Dimension;
import java.nio.file.*;
import java.util.TreeSet;
import javax.swing.*;
import keskjarj.gui.*;

/**
 * Tämä on Keskustelunjärjestäjän pääluokka, josta ohjelman suoritus alkaa.
 * Tarkoitus on, että täältä lähinnä käynnistetään GUI. 
 * Toistaiseksi myös ladataan aineistoja. Kenttänä ikkuna, jota sovellus käyttää.
 * @author mkahri
 */
public class Keskustelujarjestaja {
    
    private static JFrame ikkuna;  

    public static void main(String[] args)
    {
        Projekti projekti = new Projekti();
        
        //Path polku1 = Paths.get("../aineistoja/ElanExample.txt");
        Path polku2 = Paths.get("../aineistoja/ElanExample2.txt");
        //Path polku3 = Paths.get("../aineistoja/ElanExample3.txt"); //sis. 2 uutta riviä edell. verrattuna
        Path polku4 = Paths.get("../aineistoja/ElanExample4.txt");
        Path polku5 = Paths.get("../aineistoja/Example.mp4");
        Tallenne tallenne = new Tallenne (polku5);
    
        //projekti.tuoAnnotaatioita(polku1, tallenne);
        projekti.tuoAnnotaatioita(polku2, null);
        //projekti.tuoAnnotaatioita(polku3, null);
        projekti.tuoAnnotaatioita(polku4, null);
        
        
        
        ikkuna = new JFrame("Keskustelujarjestaja");
        ikkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension paneelinKoko = new Dimension(1400, 800);       
        ikkuna.getContentPane().add(new YlaPaneeli(paneelinKoko, projekti));
        ikkuna.pack();
        ikkuna.setVisible(true);        
    }
}
