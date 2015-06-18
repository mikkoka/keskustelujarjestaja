
package keskjarj.tieto;

import java.util.TreeSet;
import java.util.Objects;


/**
 * Abstrakti yliluokka erilaisille havaintokategorioille. Kenttinä TreeSet -joukko 
 * havaintoon liittyviä otteita sekä Havaintotyypin nimi. Toteuttaa Comparable 
 * -rajapinnan.
 */
public abstract class Havainto implements Comparable <Havainto> {

    protected String nimi;
    protected TreeSet<Ote> otteet;

    /**
     * Palauttaa havaintokategorian nimen.
     * @return havainnon nimi
     */
    public String getNimi() {        
        return this.nimi;
    }

    /**
     * Asettaa havaintokategorian nimen.
     * @param nimi
     */
    public void setNimi(String nimi) {   //lisää tarkistus, ettei muuteta nimeä samaksi kuin jo olemassa oleva!     
        this.nimi = nimi;
    }
    
    /**
     * Palauttaa TreeSet -joukon havaintokategoriaan liittyviä otteita. Otteet 
     * aakkosjärjestyksessä tunnuksen nimen mukaan. 
     * (Ei järjestä oikein tunnuksissa olevien lukujen perusteella) 
     * @return
     */
    public TreeSet<Ote> getOtteet() {        
        return otteet;
    }
    
     /**
     * Palauttaa havainnon otekokoelmasta otteen järjestysnumeron perusteella.
     * Metodi eroaa luokan Projekti vastaavasta metodista siten, että ko. metodi
     * palauttaa koko projektin havaintokokoelmasta otteen järjestysnumeron perusteella
     * @return ote järjestysnumeron perusteella
     */
    public Ote getOte(int nro) {
        if (otteet.isEmpty() || nro < 0) 
            return null;
        if (nro == 0)
            return otteet.first();
        else {                               
        Object[] oteObj = getOtteet().toArray();
        if (nro >= oteObj.length)
            return null;
        return (Ote)oteObj[nro];
                
        }
    }
            

    /**
     * Lisää otekokoelmaan Ote -olion
     * @param ote ote, joka halutaan liittää havaintokategoriaan
     * @return lisäyksen onnistuminen
     */
    public boolean lisaaOte(Ote ote) {        
        return otteet.add(ote);
    }
        
    /**
     * Lisää useita otteita kerralla havaintokategorian otekokoelmaan
     * @param otteet Ote -taulukko
     */
    public void lisaaOtteita(Ote[] otteet)
    {
        for (Ote o : otteet)
            this.lisaaOte(o);
    }
    
    /**
     * Poistaa otteen havaintokategoriaan liittyvien otteiden kokoelmasta.
     * @param ote
     * @return poiston onnistuminen
     */
    public boolean poistaOte (Ote ote) 
    {
        return otteet.remove(ote);
    }
    
    public boolean sisaltaa (Ote ote)
    {
        return otteet.contains(ote);
    }
    
    public boolean sisaltaaSamojaOtteita (Havainto toinen)
    {
        if (toinen == null)
            return false;
        for (Ote o : otteet)
            for (Ote t : toinen.getOtteet())
                if (o.equals(t))
                    return true;
        return false;
    }
    
    public void lisaaNimenPerusteella(String nimi) {

            
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
