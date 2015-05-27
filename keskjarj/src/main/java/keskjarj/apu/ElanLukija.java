/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.apu;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import keskjarj.keskjarj.Tallenne;


public class ElanLukija extends tiedostonLukija {

    
    public static void main (String[] args)
    {
        Path polku = Paths.get("../aineistoja/ElanExample2.txt");
        Tallenne tallenne = new Tallenne(polku);
        List<String> kokeilu = lueTekstitiedosto(tallenne);
        ArrayList<String> rivit;
        String[] sarakkeet;
        for (String r : kokeilu)
        {
            sarakkeet = r.split("\t"); 
            System.out.println(sarakkeet[0]);
            System.out.println(sarakkeet[2]);
            System.out.println(sarakkeet[3]);
            System.out.println(sarakkeet[4]);
            System.out.println("\n\n\n");
            
//            for (String s : sarakkeet)
//                System.out.println(s);
        }  
    } 
    
} 
