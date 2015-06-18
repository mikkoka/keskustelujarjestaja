/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.ohjelma;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;


/**
 * Lukee ja tallentaa tiedostoja. Toistaiseksi ainoastaan tekstitiedostoja. 
 * @author mkahri
 */
public class TiedostonHallinta {
    
    /**
     * Tuo tekstirivit parametrinä annetusta polusta merkkijonolistana. 
     * Lukee luotettavasti ainoastaan UTF-8 -tallennettuja tekstitiedostoja.
     * @param polku polku tekstitiedostoon, joka luetaan
     * @return rivit merkkijonolistana. Jos lukeminen epäonnistuu, palauttaa nullin.
     */
    public static List<String> tuoRivit (Path polku) 
    {
        List<String> palautus = null;        
        try {
            palautus = Files.readAllLines(polku, StandardCharsets.UTF_8);
        } catch (IOException ex) {

        }
        return palautus;
    }
    
    /**
     * Tallentaa parametrinä annetut merkkijonot tekstitiedoston riveiksi.
     * @param rivit lista merkkijonoja
     * @param polku tiedosto, johon merkkijonot tallennetaan
     * @return tallennuksen onnistuminen
     */
    public static boolean tallennaRivit (List<String> rivit, Path polku)
    {
        try {
            Files.write(polku, rivit, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
 
    /**
     * Tulostaa tekstitiedoston sisällön system.outtiin. 
     * @param polku polku tekstitiedostoon
     * @return tulostuksen onnistuminen
     */
    public static boolean tulostaTekstitiedosto(Path polku) 
    {
        List<String> rivit = tuoRivit(polku);
        if (!rivit.isEmpty()) 
        {
        for (String r : rivit) 
            System.out.println(r);
        return true;
        }
        return false;
    }
}
