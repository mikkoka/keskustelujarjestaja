/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package keskjarj.keskjarj;

import java.awt.*;
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
        
        ikkuna = new JFrame("Keskustelujarjestaja");
        ikkuna.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension paneelinKoko = new Dimension(1024, 768);       
        ikkuna.getContentPane().add(new YlaPaneeli(paneelinKoko, projekti));
        ikkuna.setResizable(true);
        ikkuna.pack();
        ikkuna.setVisible(true);        
    }
}
