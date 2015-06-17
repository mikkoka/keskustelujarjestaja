
package keskjarj.keskjarj;

import keskjarj.ohjelma.AnnotaatioidenTuoja;
import java.nio.file.*;
import java.util.*;

/**
 * Hallinnoi projektia ja sen resursseja, erityisesti havaintokokoelmaa. 
 * mm. tallennusominaisuus puuttuu toistaiseksi.
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
    * Lisää vanhoihin havaintokategorioihin uusia otteita silloin, kun 
    * sellaisia on tuotu. 
    * 
    * @param   polku   polku annotaatioita sisältävään tekstitiedostoon 
    * (tallennettu Elanilla, toisaalla annetuin ohjein)
     * @param tallenne viite mediatallenteen tiedot sisältävään Tallenne -olioon
     * @return annetun polun toimivuus
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
    
    /**
    * Lisää parametrinä saadut annotaatiot projektin havaintokokoelmaan. 
    * TreeSetin käytön ansiosta dublikaatteja ei synny, ja havainnot tulevat 
    * aakkosjärjestykseen.
    */
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
     * Palauttaa kaikki projektin havaintokokoelman Havainto-oliot.
     * @return TreeSet -joukko Havainto-olioita
     */
    public TreeSet<Havainto> getHavainnot()
    {
        return this.havainnot;
    }
    
    public Object[] getHavainnotString ()
    {
        String[] palautus = new String[havainnot.size()];
        for (int i = 0; i < havainnot.size(); i++)
            palautus[i] = this.getHavainto(i).getNimi();
        return palautus;
                    
    }
    
    public boolean havaintoOlemassa (String nimi)
    {
        for (Havainto h : havainnot)
            if (h.getNimi().equals(nimi))
                return true;
        return false;
    }
    
    public Havainto getHavainto (String nimi)
    {
        for (Havainto h : havainnot)
            if (h.getNimi().equals(nimi))
                return h;
        return null;
    }

    /**
     * Etsii projektin havaintokokoelmasta projektin kaikki otteet, 
     * ja palauttaa ne. TreeSetin käytön ansiosta joukossa ei dublikaatteja,
     * ja otteet tunnuksen mukaisessa aakkosjärjestyksessä.
     * @return TreeSet -joukko Ote-olioita
     */
    public TreeSet <Ote> getOtteet ()
    {
        TreeSet <Ote> palautus = new TreeSet();
        for (Havainto h : havainnot)
            palautus.addAll(h.getOtteet());
        return palautus;
    }
    
    public Ote[] getOtteet (int[] nrot)
    {
        Ote[] palautus;
        if (nrot == null)
            return null;
        if (havainnot.isEmpty()) 
            return null;            
        Object[] otteet = getOtteet().toArray();
        for (int nro : nrot)
            if (nro >= otteet.length || nro < 0)
                return null;
        palautus = new Ote[nrot.length];
        int laskuri = 0;
        for (int nro : nrot)
        {
            palautus[laskuri] = (Ote)otteet[nro];
            laskuri++;
        }
        return palautus;
    }
    
//    public TreeSet <Ote> getOtteet (int[] nrot)
//    {
//        TreeSet <Ote> palautus = new TreeSet();
//        if (nrot == null)
//            return null;
//        if (havainnot.isEmpty()) 
//            return null;            
//        Object[] otteet = getOtteet().toArray(); //tää on aika garmeeta ohjelmointia
//        for (int nro : nrot)
//            if (nro >= otteet.length || nro < 0)
//                return null;
//        for (int nro : nrot)
//            palautus.add(getOte(nro));
//        return palautus;
//    }
    
    /**
     * Palauttaa yksittäisen Havainto -olion projektin havaintokokoelmasta 
     * annetun "järjestysnumeron" perusteella. (Järjestysnumero viittaa Havainnon 
     * paikkaan nimen perusteella aakkosjärjestetyssä havaintokokoelmassa. 
     * Metodia tarvitaan mm. GUI-taulukon ylläpidossa.)
     * @param nro havainnon järjestysnumero aakkosjärjestetyssä havaintokokoelmassa
     * @return Havainto -olio
     */
    public Havainto getHavainto (int nro) 
    {
        if (havainnot.isEmpty() || nro >= havainnot.size() || nro < 0)
            return null; 
        Object[] temp = getHavainnot().toArray();
        return (Havainto) temp[nro];
    }
    
    /**
     * Palauttaa yksittäisen otteen projektin havaintokokoelmasta, sen 
     * järjestysnumeron aakkosjärjestetyssä otekokoelmassa perusteella.
     * @param nro otteen järjestysnumero aakkosjärjestetyssä otejoukossa
     * @return Ote -olio
     */
    public Ote getOte (int nro)
    {
        if (havainnot.isEmpty() || nro < 0) 
            return null;            
        Object[] otteet = getOtteet().toArray();
        if (nro >= otteet.length)
            return null;
        return (Ote)otteet[nro];
    }
    
        public Ote getOte (String nimi)
    {
        if (havainnot.isEmpty() || nimi.isEmpty()) 
            return null;
        for (Ote o : getOtteet()) 
        {
            if (o.getTunnus().equals(nimi)) 
            {
                return o;
            }
        }
        return null;
    }
    
    /**
     * Tulostaa projektin havainnot. Hyödyllinen guin toiminnan tarkastamisessa.
     */
    public void tulostaHavainnot ()
    {
        for (Havainto h : havainnot) {
            System.out.println(h.getNimi());
            for (Ote o : h.getOtteet())
                System.out.println(o.getTunnus());
           
        }
      
    }
    

}
