/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.keskjarj;

import keskjarj.ohjelma.AnnotaatioidenTuoja;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Hallinnoi projektia ja sen resursseja, erityisesti projektin havaintokokoelmaa.
 * @author mikko
 */
public class Projekti 
{
    private TreeSet<Havainto> havainnot;
    private ArrayList<Tallenne> tallenteet;  
    
    public Projekti ()
    {
        havainnot = new TreeSet();
        tallenteet = new ArrayList();
    }
    
    /**
    * Lisää Projektin havaintoihin uusia havaintoja tekstitiedostosta.
    * Lisää vanhoihin havaintokategorioihin. uusia otteita silloin, kun 
    * sellaisia on tuotu. 
    * 
    * @param   polku   polku annotaatioita sisältävään tekstitiedostoon 
    * (tallennettu Elanilla, toisaalla annetuin ohjein)
     * @param tallenne TÄYDENNÄ!
     * @return polun toimivuus
    */
    public boolean tuoAnnotaatioita (Path polku, Tallenne tallenne)
    {
        if(!Files.isReadable(polku))
            return false;
        AnnotaatioidenTuoja tuoja = new AnnotaatioidenTuoja(polku, tallenne);
        TreeSet<Havainto> uudetHavainnot = tuoja.tuo();
        lisaaAnnotaatiot(uudetHavainnot);   
        return true;
    }

    private void lisaaAnnotaatiot (TreeSet<Havainto> uudetHavainnot)
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
    
    /**
     * Luo uuden havaintokategorian merkkijonon perusteella, ja liittää sen 
     * projektin havaintokokoelmaan. Mikäli tarjottu nimi on jo 
     * käytössä, havaintokategorian luonti epäonnistuu.
     * 
     * @param nimi  nimi havaintokategorialle 
     * @return onnistuiko kategorian luonti
     */
    public boolean luoHaivaintokategoria(String nimi)
    {
        return havainnot.add(new OtettaKoskevaHavainto(nimi));
    }
    
    /**
     * Palauttaa kaikki projektin havaintokokoelman getHavainto-oliot.
     * @return HashSet -kokoelma Havainto-olioita
     */
    public TreeSet<Havainto> getHavainnot()
    {
        return this.havainnot;
    }

   
    public TreeSet <Ote> getOtteet ()
    {
        TreeSet <Ote> palautus = new TreeSet();
        for (Havainto h : havainnot)
            palautus.addAll(h.getOtteet());
        return palautus;
    }
    
    public Havainto getHavainto (int nro) 
    {
        if (!havainnot.isEmpty() || nro < havainnot.size()) {
            Object[] temp = getHavainnot().toArray();
            return (Havainto) temp[nro];
        } else return null;

    }
    
    public Ote getOte (int nro)
    {
        if (!havainnot.isEmpty() || nro < havainnot.size()) {
        Object[] otteet = getOtteet().toArray();
        return (Ote) otteet[nro];
        }
        else return null;
    }
    
    public void tulostaOtteet ()
    {
        for (Havainto h : havainnot) {
            System.out.println(h.getNimi());
            for (Ote o : h.getOtteet())
                System.out.println(o.getTunnus());
           
        }
      
    }
    

}
