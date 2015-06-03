/*
 * Luokasta luodaan instanssi jokaista annotaatiotiedoston tuontia varten.
 * Instanssia ei voi käyttää toisen annotaatioitiedoston tuontiin, eikä 
 * instanssiin luettavan tiedoston polkua voi muuttaa instanssin luonnin jälkeen. 
 */
package keskjarj.keskjarj;

import java.nio.file.*;
import java.util.*;

public class AnnotaatioidenTuoja extends TiedostonLukija 
{    
    List<String> rivit;
    HashSet<String> ootteet;
    HashSet<String> kategoriat;
    HashSet<Havainto> havainnoot;

    private HashMap<String, List<String>> havainnotJaOtteet; //Tässä String -muotoisina
    
    private HashMap<String, List<String>> otteetJaHavainnot; //Tässä String -muotoisina
    private HashMap<Ote, List<String>> otteet; //Tässä otteet Ote-muotoisina, havainnot edelleen String listana
    private TreeSet<String> havaintotyypit;
    private Path polku;
    
    public AnnotaatioidenTuoja(Path polku)
    {
        otteetJaHavainnot = new HashMap();
        havaintotyypit = new TreeSet();
        otteet = new HashMap();
        this.polku = polku;
    }
    
    public HashSet<Havainto> tuo() 
    {
        Tallenne tallenne = new Tallenne(polku);
        rivit = tuoRivit(polku);
        

        eritteleRivit();
        luoOtteet(tallenne);
        return luoHavainnot();
    }
    
    private void listaaKategoriat()
    {
        String[] rivi;
        
        for (String r : rivit) 
        {
            rivi = r.split("\t");
            kategoriat.add(rivi[0]);                 
        }
    }
    
        private void listaaOtteet()
    {
        String[] rivi;
        
        for (String r : rivit) 
        {
            rivi = r.split("\t");
            ootteet.add(rivi[2] + "-" + rivi[3]);                 
        }
    }

    private void eritteludfsdfRivit(Tallenne tallenne) 
    {  
        listaaKategoriat();
        
        
        
        //havaintolistat tulevat HashMappin otteetJaHavainot
        List<String> havaintolista; //Näitä luodaan siis yksi jokaiselle otteelle

        //Väliaikaisia
        String[] rivi; String otteenAjat, kategoria;

        for (String r : rivit) 
        {
            rivi = r.split("\t");

            //katenoidaan tekstitiedostosta saadut ootteet (sarakkeissa 3 ja 4)
            otteenAjat = rivi[2] + "-" + rivi[3];

            //tekstitiedossa oli 1. sarakkeessa havaintokategoria
            kategoria = rivi[0];
            
            havainnoot.add(new Havainto())
            
            
            

            //lisätään uudet havaintokategoriat havaintotyyppien listaan
            havaintotyypit.add(kategoria);

            //kokeillaan, mitä tulee pyytämällä merkkijonoon *otteenAjat* liittyvää havaintolistaa
            havaintolista = otteetJaHavainnot.get(otteenAjat);

            //jos havaintolistaa ko. aikavälille ei luotu, luodaan ja lisätään havainto 
            if (havaintolista == null) {
                havaintolista = new ArrayList();
                havaintolista.add(kategoria);
            } //jos aikaväliä varten on jo havaintolista, lisätään havainto
            else {
                havaintolista.add(kategoria);
            }
            otteetJaHavainnot.put(otteenAjat, havaintolista);
        }
    }
        
        
        
        
        
        
        
            private void eritteleRivit() 
    {
        
        
        
        //havaintolistat tulevat HashMappin otteetJaHavainot
        List<String> havaintolista; //Näitä luodaan siis yksi jokaiselle otteelle

        //Väliaikaisia
        String[] rivi; String otteenAjat, kategoria;

        for (String r : rivit) 
        {
            rivi = r.split("\t");

            //katenoidaan tekstitiedostosta saadut ootteet (sarakkeissa 3 ja 4)
            otteenAjat = rivi[2] + "-" + rivi[3];

            //tekstitiedossa oli 1. sarakkeessa havaintokategoria
            kategoria = rivi[0];

            //lisätään uudet havaintokategoriat havaintotyyppien listaan
            havaintotyypit.add(kategoria);

            //kokeillaan, mitä tulee pyytämällä merkkijonoon *otteenAjat* liittyvää havaintolistaa
            havaintolista = otteetJaHavainnot.get(otteenAjat);

            //jos havaintolistaa ko. aikavälille ei luotu, luodaan ja lisätään havainto 
            if (havaintolista == null) {
                havaintolista = new ArrayList();
                havaintolista.add(kategoria);
            } //jos aikaväliä varten on jo havaintolista, lisätään havainto
            else {
                havaintolista.add(kategoria);
            }
            otteetJaHavainnot.put(otteenAjat, havaintolista);
        }
    }
            
    private void jotainKauheeta() 
    {
        
        
        
        //havaintolistat tulevat HashMappin havainnotJaOtteet
        List<String> tralalaa; //Näitä luodaan siis yksi jokaiselle havaintotyypille

        //Väliaikaisia
        String[] rivi; String otteenAjat, kategoria;

        for (String r : rivit) 
        {
            rivi = r.split("\t");

            //katenoidaan tekstitiedostosta saadut ootteet (sarakkeissa 3 ja 4)
            otteenAjat = rivi[2] + "-" + rivi[3];

            //tekstitiedossa oli 1. sarakkeessa havaintokategoria
            kategoria = rivi[0];

            //lisätään uudet havaintokategoriat havaintotyyppien listaan
            //havaintotyypit.add(kategoria);

            //kokeillaan, mitä tulee pyytämällä merkkijonoon *kategoria* liittyvää havaintolistaa
            tralalaa = otteetJaHavainnot.get(kategoria);

            //jos havaintolistaa ko. aikavälille ei luotu, luodaan ja lisätään havainto 
            if (tralalaa == null) {
                tralalaa = new ArrayList();
                tralalaa.add(otteenAjat);
            } //jos aikaväliä varten on jo havaintolista, lisätään havainto
            else {
                tralalaa.add(otteenAjat);
            }
            havainnotJaOtteet.put(kategoria, tralalaa);
        }
    }
            
            
            
            
            
    private void luoOtteet(Tallenne tallenne)
    {
        Ote temp; String[] ajatString;
        
        for (String o : otteetJaHavainnot.keySet()) 
        {            
            ajatString = o.split("-");            
            Double alku = Double.parseDouble(ajatString[0]);
            Double loppu = Double.parseDouble(ajatString[1]);
           
            temp = new Ote(tallenne, alku, loppu);            
            otteet.put(temp, otteetJaHavainnot.get(o));
        }
    }
    
    private HashSet<Havainto> luoHavainnot()
    {
        List<String> havaintolista;
        
        for (Ote o : otteet.keySet())
            for (String h : havaintotyypit)
                havaintolista = o.
            
            
            
    }
    
   

//    private static HashMap<Ote, List<HavainHavaintoOtteet(HashMap<String, List<String>> m, Tallenne t) {
//
//        HashMap<Ote, List<Havainto>> = new HashMap();
//        List<String> htyypit;
//        List<Havainto> joku();
//        Ote ote;
//        String[] ootteet;
//        Double alku, loppu;
//
//        for (String s : m.keySet()) {
//            ootteet = s.split("-");
//            alku = Double.parseDouble(ootteet[0]);
//            loppu = Double.parseDouble(ootteet[1]);
//
//            htyypit = m.get(s);
//
//            ote = new Ote(t, alku, loppu);
//
//            for (String z : htyypit) {
//                joku.add(new OtettaKoskevaHavainto(z));
//                System.out.println(z);
//            }
//            palautus.put(ote, joku);
//        }
//        return palautus;
//    }

    public List<Double> listaaAlut() {
        
        return listaaAjat(otteet, 0);
    }

    public static List<Double> listaaLoput(HashMap<String, List<String>> otteet) {
        return listaaAjat(otteet, 1);
    }
    
    private List listaaSarake(int nro) {

        String[] ajat;
        List<Double> palautus = new ArrayList();

        for (String o : otteetJaHavainnot.keySet()) {

            //Ajat ovat katenoituna merkkijonona, "-" erottimena
            ajat = o.split("-");
            palautus.add(Double.parseDouble(ajat[nro]));

        }

        Collections.sort(palautus);
        return palautus;
    }

    public static void main(String[] args) {

        Path polku = Paths.get("../aineistoja/ElanExample.txt");
        System.out.println("\nTekstitiedosto raakana:");
        tulostaTekstitiedosto(polku);

        Tallenne tallenne = new Tallenne(polku);

        List<String> rivit = tuoRivit(tallenne);

//        HashMap<Ote, List<HavaintoTyyppi>> kokeiluHavainto
//
//        System.out.println(kokeilu.get(kokeilu.entrySet()));

//        
//        
//
//
//        HashMap<String, List<String>> mappi = kokoaOtteet(rivit);
////
////        System.out.println("\nAjat uniikkeina merkkijonoina:\n");
////        for (String p : mappi.keySet()) {
////            System.out.println(p);
////        }
////
//        System.out.println("\nHavainnot ilman aikoja, joihin ne liittyvät:\n");
//        for (List<String> p : mappi.values()) {
//            System.out.println(p);
//        }
//
//        List<Double> alut = listaaAlut(mappi);
//        List<Double> loput = listaaLoput(mappi);
//
//        System.out.println("\nAlut:");
//        for (Double a : alut) {
//            System.out.println(a);
//        }
//
//        System.out.println("\nLoput:");
//        for (Double l : loput) {
//            System.out.println(l);
//        }
    }
}
