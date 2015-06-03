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
        havainnot = new HashSet<Havainto>();
        tallenteet = new ArrayList();
    }
    
    public void tuoAnnotaatioita (Path polku)
    {
        AnnotaatioidenTuoja tuoja = new AnnotaatioidenTuoja(polku);
        HashSet<Havainto> uudetHavainnot = tuoja.tuo();
    }
}
