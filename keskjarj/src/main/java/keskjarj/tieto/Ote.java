
package keskjarj.tieto;

import java.util.Objects;

/**
 * Luokka tallettaa tiedot otteesta ja sisältää tiedon mediatiedostosta, 
 * johon se liittyy, sekä sen alku- ja loppuajat kyseisessä tiedostossa. 
 * Toteuttaa rajapinnan Comparable.   
 */
public class Ote implements Comparable<Ote>
{
    private Tallenne tallenne;
    private Osanottaja[] osanottajat;
    private Double alku, loppu;
    private String ajat, tiedosto, tunnus;
    
    /**
     * Otteita luo ainoastaan luokka AnnotaatioidenTuoja, ja Oteen konstruktori 
     * on sen tarpeisiin mukautunut. Kenttiä on mm. Tallenne -oliolle, otteen
     * alku- ja loppuajoille sekä merkkijonoille, joita käytetään HashCoden 
     * luomiseen sekä alustavan otekohtaisen tunnuksen luomiseen.
     * @param tallenne Tallenne -olio sisältäen linkin mediatiedostoon
     * @param alku otteen alku tallenteella sekunteina tallenteen alusta
     * @param loppu otteen loppu tallenteella
     * @param ajat alku ja loppu katenoituna merkkijonona "-" merkin kanssa. 
     * @param tiedosto annotaatiot sisältäneen tiedoston tai mediatiedoston nimi 
     * merkkijonona, ilman polkua (ks. luokan AnnotaatioidenTuoja lähdekoodi). 
     * Käytetään mm. alustavan tunnuksen luomiseen. 
     */
    public Ote (Tallenne tallenne, Double alku, Double loppu, String ajat, String tiedosto)
    {
        this.tallenne = tallenne;
        this.ajat = ajat;
        this.tiedosto = tiedosto;
        
        if (alku < loppu && alku > 0)
        {
            this.alku = alku;
            this.loppu = loppu;
        } else 
        {   this.alku = 0.0; // mieti tätä vielä
            this.loppu = 0.0;
        }
    }
    
    /**
     * Palauttaa kentän tallenne sisällön.
     * @return tallenne
     */
    public Tallenne getTallenne ()
    {
        return this.tallenne;
    }
    
    /**
     * Asettaa tallenteen.
     * @param tallenne
     */
    public void setTallenne(Tallenne tallenne) 
    {
        this.tallenne = tallenne;
    }
    
    /**
     * Palauttaa otteen alkuajan.
     * @return alku sekunteina tallenteen alusta
     */
    public Double getAlku ()
    {
        return this.alku;
    }
    
    /**
     * Palauttaa otteen loppuajan.
     * @return loppu sekunteina tallenteen alusta.
     */
    public Double getLoppu()
    {
        return this.loppu;
    }
        
    /**
     * Asettaa otteen alkuajan. Toistaiseksi ei käytössä.
     * @param alku alku sekunteina tallenteen alusta
     */
    public void setAlku (Double alku)
    {
        if (alku < this.loppu)
            this.alku = alku;
    }
    
    /**
     * Asettaa otteen loppuajan. Toistaiseksi ei käytössä.
     * @param loppu loppu sekunteina tallenteen alusta
     */
    public void setLoppu(Double loppu)
    {
        if (loppu > this.alku)
            this.loppu = loppu;
    }
    
    /**
     * Palauttaa kentän ajat sisältämän merkkijonon. Onko tarpeellinen?
     * @return alku ja loppu väliviivan kanssa katenoituna merkkijonona
     */
    public String getAjat()
    {
        return this.ajat;
    }
        
    /**
     * Palauttaa otteen tunnuksen
     * @return merkkijono
     */
    public String getTunnus()
    {
        if (this.tunnus == null)
            return this.tiedosto + " " + this.ajat;
        else return this.tunnus;
    }
    
    /**
     * Asettaa otteen tunnuksen
     * @param tunnus uusi tunnus
     */
    public void setTunnus(String tunnus)
    {
        this.tunnus = tunnus;
    }
    
    @Override
    public int hashCode() 
    {
        return (ajat + tiedosto).hashCode();
    }

    @Override
    public boolean equals(Object toinen) 
    {
        if (toinen == null)
        {
            return false;
        }

        if (getClass() != toinen.getClass()) 
        {
            return false;
        }
        
        Ote ote2 = (Ote) toinen;
        
        if (!Objects.equals(this.alku, ote2.alku)) 
        {
            return false;
        }
        
        if (!Objects.equals(this.loppu, ote2.loppu)) 
        {
            return false;
        }
        
        return true;
    } 
    
    @Override
    public int compareTo(Ote o) {
        return this.getTunnus().toLowerCase().compareTo(o.getTunnus().toLowerCase());
    }
}
