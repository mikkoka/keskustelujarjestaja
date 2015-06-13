/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.keskjarj;

import java.util.TreeSet;
import java.util.Objects;


/**
 *
 * @author mikko
 */
public abstract class Havainto implements Comparable <Havainto> {

    protected String nimi;
    protected TreeSet<Ote> otteet;

    public String getNimi() {        
        return this.nimi;
    }

    public void setNimi(String nimi) {        
        this.nimi = nimi;
    }
    
    public TreeSet<Ote> getOtteet() {        
        return otteet;
    }

    public boolean lisaaOte(Ote ote) {        
        return otteet.add(ote);
    }
        
    public void lisaaOtteita(Ote[] otteet)
    {
        for (Ote o : otteet)
            this.lisaaOte(o);
    }
    
    public boolean poistaOte (Ote ote) 
    {
        return otteet.remove(ote);
    }
    
        /**
     * Palauttaa pyydettyyn havaintokategoriaan sisällytetyt otteet. 
     * Saatetaan siirtää luokkaan Havainto, tai poistaa tarpeettomana
     * @param havainto TÄYDENNÄ, KORJAA!
     * @return  HashSet -kokoelma Ote-olioita
     */
    public TreeSet <Ote> getOtteet (Havainto havainto)
    {
        return havainto.otteet;       
    }

    @Override
    public int hashCode() {        
        return nimi.hashCode();
    }

    @Override
    public boolean equals(Object toinen) {
        
        if (toinen == null) {
            return false;
        }

        if (getClass() != toinen.getClass()) {
            return false;
        }

        Havainto havainto2 = (Havainto) toinen;

        if (!Objects.equals(this.nimi, havainto2.nimi)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Havainto h) {
        return this.getNimi().compareTo(h.getNimi());
    }
}
