/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.apu;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import keskjarj.keskjarj.Tallenne;


public class ElanLukija extends tiedostonLukija {

    
    public static void main (String[] args)
    {
        Path polku = Paths.get("../aineistoja/ElanExample2.txt");
        Tallenne tallenne = new Tallenne(polku);
        List<String> rivit = lueTekstitiedosto(tallenne);
        String[] sarakkeet;
        ArrayList<String> havainnot = new ArrayList();
        ArrayList<String> alut = new ArrayList();
        ArrayList<String> loput = new ArrayList();
        ArrayList<String> kommentit = new ArrayList();
        for (int r = 0; r < rivit.size(); r++) 
        {
            sarakkeet = rivit.get(r).split("\t");
            havainnot.add(sarakkeet[0]);
            alut.add(sarakkeet[2]);
            loput.add(sarakkeet[3]);
            kommentit.add(sarakkeet[4]);
        }
        
        HashMap<String, ArrayList<String>> mappi = new HashMap();


            System.out.println(havainnot);
            System.out.println(alut);
            System.out.println(loput);
            System.out.println(kommentit);
            
//            for (String s : sarakkeet)
//                System.out.println(s);
 
    } 
    
} 
