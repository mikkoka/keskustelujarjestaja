/*
 * Luokasta luodaan instanssi jokaista annotaatiotiedoston tuontia varten.
 * Instanssia ei voi käyttää toisen annotaatioitiedoston tuontiin, eikä 
 * instanssiin luettavan tiedoston polkua voi muuttaa instanssin luonnin jälkeen. 
 * Luokan koodi on aivan turhan monimutkaista toistaiseksi. Javadoccia ei kannattane
 * hioa kovin paljoa ennen kuin työnjako metodien välillä lopullinen.
 */
package keskjarj.keskjarj;

import java.nio.file.*;
import java.util.*;

public class AnnotaatioidenTuoja extends TiedostonLukija 
{    
    private List<String> rivit;
    private HashSet<Ote> otteet;
    private HashMap<String, List<String>> kategoriatJaAikajaksot; //Tässä String -muotoisina
    private Path polku;
    
    public AnnotaatioidenTuoja(Path polku)
    {
        kategoriatJaAikajaksot = new HashMap();
        otteet = new HashSet();
        this.polku = polku;
    }
    
    public HashSet<Havainto> tuo() 
    {
        Tallenne tallenne = new Tallenne(polku);
        rivit = tuoRivit(polku);        
        luoOtteet(listaaOtteet(), tallenne);
        eritteleRivit();
        return luoHavainnot();
    }

    private HashSet<String> listaaOtteet()
    {
        String[] rivi;
        HashSet<String> palautus = new HashSet();
        
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
        
    
    private void luoOtteet(HashSet<String> stringOtteet, Tallenne tallenne)
    {

        String tiedostonimi;
        for (String o : stringOtteet)
        {
            Double alku = puraAikajakso(o, 0); 
            Double loppu = puraAikajakso(o, 1);
            tiedostonimi = tallenne.getTiedostoNimi();            
            
            otteet.add(new Ote(tallenne, alku, loppu, o, tiedostonimi));
        }        
    }
    
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
        
    private HashSet<Havainto> luoHavainnot()
    {
        Havainto havainto; List<String> aikaJaksot;
        HashSet<Havainto> palautus = new HashSet();
        
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


    public static void main(String[] args) {

        Path polku = Paths.get("../aineistoja/ElanExample.txt");
        System.out.println("\nTekstitiedosto raakana:");
        tulostaTekstitiedosto(polku);
        
        AnnotaatioidenTuoja tuoja = new AnnotaatioidenTuoja(polku);
        HashSet<Havainto> kokeilu;
        kokeilu = tuoja.tuo();
        for (Havainto h : kokeilu)
        {
            System.out.println(h.nimi);
            for (Ote o : h.otteet)
                System.out.println(o.getTunnus());
        }
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
