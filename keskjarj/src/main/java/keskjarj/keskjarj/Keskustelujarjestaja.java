/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package keskjarj.keskjarj;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

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
        Projekti projekti = new Projekti();
        
        Path polku1 = Paths.get("../aineistoja/ElanExample.txt");
        Path polku2 = Paths.get("../aineistoja/ElanExample2.txt");
        Path polku3 = Paths.get("../aineistoja/ElanExample3.txt"); //sis. 2 uutta riviä edell. verrattuna
        Path polku4 = Paths.get("../aineistoja/ElanExample4.txt");
        
        projekti.tuoAnnotaatioita(polku1);
        projekti.tuoAnnotaatioita(polku2);
        projekti.tuoAnnotaatioita(polku3);
        projekti.tuoAnnotaatioita(polku4);
        
        HashSet<Havainto> kokeilu = projekti.havainnot();
        
        System.out.println("\nOtteet havaintokategorioittain:");
        for (Havainto h : kokeilu)
        {
            System.out.println(h.nimi);
            for (Ote o : h.otteet)
                System.out.println(o.getTunnus());
        }
        
        System.out.println("\nHavaintokategoriaan \"hehe\" liittyvät otteet:");
        HashSet<Ote> kokeilu3 = projekti.otteet("hehe");
        for (Ote o : kokeilu3)
            System.out.println(o.getTunnus());
        
        System.out.println("\nHavaintokategoriaan \"Trenscraption\" liittyvät otteet:");
        HashSet<Ote> kokeilu2 = projekti.otteet("Trenscraption");
        for (Ote o : kokeilu2)
            System.out.println(o.getTunnus());
        
        
    }
}
