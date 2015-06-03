/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.keskjarj;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import keskjarj.keskjarj.Tallenne;

/**
 *
 * @author mkahri
 */
public abstract class TiedostonLukija {
    
    public static List<String> tuoRivit (Path polku) 
    {
        List<String> palautus = null;        
        try {
            palautus = Files.readAllLines(polku, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println("ÄHH! (Sanoo tiedostonLukija.lueTekstitiedosto.)");
        }
        return palautus;
    }  
 
    public static void tulostaTekstitiedosto(Path polku) 
    {
        List<String> rivit = tuoRivit(polku);
        for (String r : rivit) {
            System.out.println(r);
        }
    }
}
