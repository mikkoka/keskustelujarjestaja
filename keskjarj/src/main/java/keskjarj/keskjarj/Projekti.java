/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.keskjarj;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author mikko
 */
public class Projekti 
{
    private HashSet<Havainto> havainnot;
    private ArrayList<Tallenne> tallenteet;  
    
    public Projekti ()
    {
        havainnot = new HashSet();
        tallenteet = new ArrayList();
    }
    
    public void tuoAnnotaatioita (Path polku)
    {
        AnnotaatioidenTuoja tuoja = new AnnotaatioidenTuoja(polku);
        HashSet<Havainto> uudetHavainnot = tuoja.tuo();
        lisaaAnnotaatiot(uudetHavainnot);                  
    }
    /**
    * Lisää Projektin havaintoihin uudet havainnot. Lisää vanhoihin havaintokategorioihin
    * uusia otteita silloin, kun sellaisia on tuotu.
    */
    private void lisaaAnnotaatiot (HashSet<Havainto> uudetHavainnot)
    {        
        for (Havainto uusi : uudetHavainnot) 
            if (!havainnot.contains(uusi))
                havainnot.add(uusi);
            else
            {
                for (Havainto h : havainnot)
                    if (h.equals(uusi))
                        for (Ote  o : uusi.getOtteet())
                            h.lisaaOte(o);
            }
    }
    
    public boolean luoHaivaintokategoria(String nimi)
    {
        return havainnot.add(new OtettaKoskevaHavainto(nimi));
    }
    
    public HashSet<Havainto> havainnot()
    {
        return this.havainnot;
    }
    
    public void jarjestele(Ote[] ... otteet)
    {
        
    }
}
