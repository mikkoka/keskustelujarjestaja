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
import java.util.HashSet;

/**
 * Hallinnoi projektia ja sen resursseja, erityisesti projektin havaintokokoelmaa.
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
        HashSet<Havainto> uudetHavainnot = tuoja.tuo();
        lisaaAnnotaatiot(uudetHavainnot);   
        return true;
    }

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
     * Palauttaa kaikki projektin havaintokokoelman havainto-oliot.
     * @return HashSet -kokoelma Havainto-olioita
     */
    public HashSet<Havainto> havainnot()
    {
        return this.havainnot;
    }

   
    public HashSet <Ote> kaikkiOtteet ()
    {
        HashSet <Ote> palautus = new HashSet();
        HashSet <Ote> temp;
        for (Havainto h : havainnot)
        {
            temp = h.getOtteet();
            for (Ote t : temp)
                palautus.add(t);
        }
        return palautus;
    }
    
    public Havainto havainto (int nro) 
    {
        if (!havainnot.isEmpty() || nro < havainnot.size()) {
            Object[] temp = havainnot().toArray();
            return (Havainto) temp[nro];
        } else return null;

    }
    
    public Ote ote (int nro)
    {
        if (!havainnot.isEmpty() || nro < havainnot.size()) {
        Object[] otteet = kaikkiOtteet().toArray();
        return (Ote) otteet[nro];
        }
        else return null;
    }
    

}
