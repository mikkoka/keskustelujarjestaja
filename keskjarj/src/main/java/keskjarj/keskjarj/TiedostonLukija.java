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

/**
 * Lukee tiedostoja.
 * @author mkahri
 */
public class TiedostonLukija {
    
    public static List<String> tuoRivit (Path polku) 
    {
        List<String> palautus = null;        
        try {
            palautus = Files.readAllLines(polku, StandardCharsets.UTF_8);
        } catch (IOException ex) {

        }
        return palautus;
    }  
 
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
