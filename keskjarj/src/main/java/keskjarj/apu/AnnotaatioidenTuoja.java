/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package keskjarj.apu;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import keskjarj.keskjarj.Ote;
import keskjarj.keskjarj.Tallenne;

public class AnnotaatioidenTuoja extends tiedostonLukija
{
    
    
    
    public static void main(String[] args) {
        Path polku = Paths.get("../aineistoja/ElanExample.txt");
        Tallenne tallenne = new Tallenne(polku);
        List<String> rivit = lueTekstitiedosto(tallenne);
        
        System.out.println("\nTekstitiedosto raakana:\n");
        for (String r : rivit)
            System.out.println(r);
        
        HashMap<String, List<String>> mappi = listaaOtteet(rivit);
        
        
        System.out.println("\nAjat uniikkeina merkkijonoina:\n");
        for (String p : mappi.keySet())
            System.out.println(p);
        
        System.out.println("\nHavainnot ilman aikoja:\n");
        for (List<String> p : mappi.values())
            System.out.println(p);
        
        List<Double> alut = listaaAlut(mappi);
        List<Double> loput = listaaLoput(mappi);
        
        System.out.println("\nAlut:");
        for (Double a : alut)
            System.out.println(a);
        
        System.out.println("\nLoput:");
        for (Double l : loput)
            System.out.println(l);
    }
    
    
    protected static HashMap<String, List<String>> listaaOtteet(List<String> rivit)
    {
        HashMap<String, List<String>> palautus = new HashMap(); //HashMapissa jokainen avain ainutkertainen
        List<String> havainnot; String ajat, havainto; String[] sarakkeet;
        
        for (String r : rivit)
        {
            sarakkeet = r.split("\t");
            
            ajat = sarakkeet[2] + "-" + sarakkeet[3];
            havainto = sarakkeet[0];
            
            havainnot = palautus.get(ajat);
            
            if (havainnot != null)
            {
                havainnot.add(havainto);
                palautus.replace(ajat, havainnot);
            }
            else
            {
                havainnot = new ArrayList();
                havainnot.add(havainto);
                palautus.put(ajat, havainnot);
            }
        }
        return palautus;
    }
    
    public static List<Double> listaaAlut (HashMap<String, List<String>> otteet)
    {
        return listaaAjat(otteet, 0);
    }
    
    public static List<Double> listaaLoput (HashMap<String, List<String>> otteet)
    {
        return listaaAjat(otteet,1);
    }
    
    private static List<Double> listaaAjat (HashMap<String, List<String>> otteet, int nro)
    {
        List<Double> palautus = new ArrayList();
        String[] ajat;
        
        for (String o : otteet.keySet()) {
            
            ajat = o.split("-");
            palautus.add(Double.parseDouble(ajat[nro]));
        }
        
        Collections.sort(palautus);
        return palautus;
    }
    
    
    
//    protected static ArrayList<Ote> teeLuokat (HashMap<String, ArrayList<String>> mappi)
//    {
//        mappi.values()
//    }
}
