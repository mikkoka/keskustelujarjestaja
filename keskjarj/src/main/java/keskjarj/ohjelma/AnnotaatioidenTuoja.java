
package keskjarj.ohjelma;

import java.nio.file.*;
import java.util.*;
import keskjarj.tieto.Havainto;
import keskjarj.tieto.Ote;
import keskjarj.tieto.OtettaKoskevaHavainto;
import keskjarj.tieto.Tallenne;

/**
 * Luokasta luodaan instanssi jokaista annotaatiotiedoston tuontia varten.
 * Instanssia ei voi käyttää toisen annotaatioitiedoston tuontiin, eikä 
 * instanssiin luettavan tiedoston polkua voi muuttaa instanssin luonnin jälkeen. 
 * Käyttää tekstitiedostojen lukemiseen yliluokan metodia.
 * 
 * @see    keskjarj.keskjarj.TiedostonHallinta
 */
public class AnnotaatioidenTuoja extends TiedostonHallinta 
{    
    private List<String> rivit;
    private TreeSet<Ote> otteet;
    private HashMap<String, List<String>> kategoriatJaAikajaksot; //Tässä String -muotoisina
    private Path polku;
    private Tallenne tallenne;
    
    /**
     * Konstruktori, jota käytetään tilanteessa, että halutaan lukea tietoja tekstitiedostosta.
     * @param polku
     * @param tallenne
     */
    public AnnotaatioidenTuoja(Path polku, Tallenne tallenne)
    {
        kategoriatJaAikajaksot = new HashMap();
        otteet = new TreeSet();
        this.polku = polku;
        this.tallenne = tallenne;
    }
    
    /**
     * Tämä konstruktori on olemassa tilannetta varten, jossa halutaan lukea projektiin
     * tietoja, jotka ovat merkkijonolistan, ei tekstitiedoston muodossa.
     * @param rivit
     * @param tallenne
     */
    public AnnotaatioidenTuoja(List<String> rivit, Tallenne tallenne)
    {
        kategoriatJaAikajaksot = new HashMap();
        otteet = new TreeSet();
        this.polku = null;
        this.tallenne = tallenne;
        this.rivit = rivit;
    }
    
    /**
     * Lukee merkkijonoja ja tuottaa niitten perusteella havaintoja ja otteita.  
     * @return  Havainto -olioita sisältävä TreeSet
     */
    public TreeSet<Havainto> tuo() 
    {
        // Komentojen suoritusjärjestys ei ole yhdentekevä; muuta harkiten
        if (this.rivit == null)
            rivit = tuoRivit(polku);
        if (rivit != null) {
            luoOtteet(listaaOtteet(), tallenne);
            eritteleRivit();
            return luoHavainnot();
        }
        return null;
    }
     /**
     * Lukee merkkijonoja ja poimii niistä ainutkertaisten otteiden aikatiedot.
     * Tekstitiedostossa voi esiintyä sama aikaväli useaan kertaan.
     * TreeSetin käyttö estää useitten samaan aikaväliin liittyvien otteiden 
     * luomisen.
     * @return  ainutkertaisia merkkijonoja sisältävä TreeSet
     */
    private TreeSet<String> listaaOtteet()
    {
        if (rivit.isEmpty())
            return null;
        String[] rivi;
        TreeSet<String> palautus = new TreeSet();
        
        for (String r : rivit) 
        {
            rivi = r.split("\t");
            palautus.add(muodostaAikajakso(rivi[2], rivi[3]));                 
        }
        return palautus;
    }
    
    private String muodostaAikajakso(String aika1, String aika2)
    {
        return aika1 + "-" + aika2;
    }
    
    private Double puraAikajakso(String aikaJakso, int sarake)
    {
        String[] ajat = aikaJakso.split("-");
        return Double.parseDouble(ajat[sarake]);
    }
    
    protected String[] puraAikajakso(String jakso) 
    {
        return jakso.split("-");
        
    }  
    
     /**
     * Luo otteita metodin listaaOtteet() tuottaman merkkijonolistan perusteella,
     * lisäten kuhunkin otteeseen myös parametrinä saadun mediatiedostotiedon.
     * Otteet tallennetaan instanssin kenttään otteet.
     */
    private void luoOtteet(TreeSet<String> stringOtteet, Tallenne tallenne)
    {
        String tiedostonimi;
        for (String o : stringOtteet)
        {
            Double alku = puraAikajakso(o, 0); 
            Double loppu = puraAikajakso(o, 1);
            if (!(tallenne == null))
                tiedostonimi = tallenne.getTiedostoNimi();
            else tiedostonimi = polku.getFileName().toString();
            
            otteet.add(new Ote(tallenne, alku, loppu, o, tiedostonimi));
        }        
    }
    
     /**
     * Lukee tekstitiedostosta saaduilta riveiltä havainnot ja niihin liittyvät
     * aikajaksot instanssin kenttään kategoriatJaAikajaksot
     */
    private void eritteleRivit() 
    {       
        //havaintolistat tulevat HashMappin havainnotJaOtteet
        List<String> kategoriaanLiittyvatOtteet; String[] rivi;

        for (String r : rivit) 
        {
            rivi = r.split("\t");
            
            // Rivin 1. sarakkeessa on aina kategoria, johon sarakkeitten 3 ja 4 ajat liittyvät
            kategoriaanLiittyvatOtteet = kategoriatJaAikajaksot.get(rivi[0]);

            //Jos aikajaksolistaa ko. kategorialle ei luotu, luodaan ja lisätään
            if (kategoriaanLiittyvatOtteet == null) {
                kategoriaanLiittyvatOtteet = new ArrayList();
                kategoriaanLiittyvatOtteet.add(muodostaAikajakso(rivi[2], rivi[3]));
            } //jos aikaväliä varten on jo havaintolista, lisätään havainto
            else {
                kategoriaanLiittyvatOtteet.add(muodostaAikajakso(rivi[2], rivi[3]));
            }
            kategoriatJaAikajaksot.put(rivi[0], kategoriaanLiittyvatOtteet);
        }
    }
    
     /**
     * Luo OtettaKoskevaHavainto -oliot jokaista kategoriatJaAikajaksot -kentän
     * avainarvoa varten. liittää viitteet Ote -olioihin kategoriatJaAikajaksot 
     * -kentän avainarvoihin liittyvien tietojen perusteella
     */
    private TreeSet<Havainto> luoHavainnot()
    {
        Havainto havainto; List<String> aikaJaksot;
        TreeSet<Havainto> palautus = new TreeSet();
        
        for (String k : kategoriatJaAikajaksot.keySet())
        {
            havainto = new OtettaKoskevaHavainto(k);
            for (Ote o : otteet)
            {
                aikaJaksot = kategoriatJaAikajaksot.get(k);
                for (String a : aikaJaksot)
                {
                    if (o.getAjat().equals(a))
                        havainto.lisaaOte(o);
                }
            }
            palautus.add(havainto);      
        }
        return palautus;       
    }
}  
//    private List listaaSarake(int nro) 
//    {
//
//        String[] ajat;
//        List<Double> palautus = new ArrayList();
//
//        for (String o : otteetJaHavainnot.keySet()) {
//
//            //Ajat ovat katenoituna merkkijonona, "-" erottimena
//            ajat = o.split("-");
//            palautus.add(Double.parseDouble(ajat[nro]));
//
//        }
//
//        Collections.sort(palautus);
//        return palautus;
//    }
