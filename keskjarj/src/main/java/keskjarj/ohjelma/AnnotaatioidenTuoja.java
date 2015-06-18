
package keskjarj.ohjelma;

import java.nio.file.*;
import java.util.*;
import keskjarj.keskjarj.Havainto;
import keskjarj.keskjarj.Ote;
import keskjarj.keskjarj.OtettaKoskevaHavainto;
import keskjarj.keskjarj.Tallenne;

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
    
    public AnnotaatioidenTuoja(Path polku, Tallenne tallenne)
    {
        kategoriatJaAikajaksot = new HashMap();
        otteet = new TreeSet();
        this.polku = polku;
        this.tallenne = tallenne;
    }
    
    public AnnotaatioidenTuoja(List<String> rivit, Tallenne tallenne)
    {
        kategoriatJaAikajaksot = new HashMap();
        otteet = new TreeSet();
        this.polku = null;
        this.tallenne = tallenne;
        this.rivit = rivit;
    }
    
    /**
     * Lukee tekstitiedostosta havaintoja ja otteita ja luo niistä olioita
     * @return  Havainto -olioita sisältävä HashSet
     */
    public TreeSet<Havainto> tuo() 
    {
        // Komentojen suoritusjärjestys ei ole yhdentekevä; muuta harkiten
        if (this.rivit == null)
            rivit = tuoRivit(polku);        
        luoOtteet(listaaOtteet(), tallenne);
        eritteleRivit();
        return luoHavainnot();
    }

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
