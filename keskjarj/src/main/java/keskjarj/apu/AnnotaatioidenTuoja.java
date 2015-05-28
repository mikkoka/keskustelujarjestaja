/*
 * Tää on tällä hetkellä kauhee katastrofi.
 */
package keskjarj.apu;

import java.nio.file.*;
import java.util.*;
import keskjarj.keskjarj.*;

public class AnnotaatioidenTuoja extends TiedostonLukija {

    public static HashMap<Ote, List<HavaintoTyyppi>> tuo(Path polku) {

        Tallenne tallenne = new Tallenne(polku);
        List<String> rivit = lueTekstitiedosto(tallenne);
        HashMap<String, List<String>> mappi = kokoaOtteet(rivit);
        return luoOtteet(mappi, tallenne);
    }

    private static HashMap<String, List<String>> kokoaOtteet(List<String> rivit) {
        //HashMapissa jokainen avainarvo ainutkertainen, sen vuoksi sopii tähän
        HashMap<String, List<String>> palautus = new HashMap();

        //havaintolista otteeseen liittyville havainnoille
        List<String> havaintolista;

        //Väliaikaisia
        String[] rivi;
        String aika, kategoria;

        for (String r : rivit) {
            rivi = r.split("\t");

            //katenoidaan tekstitiedostosta saadut ajat (sarakkeissa 3 ja 4)
            aika = rivi[2] + "-" + rivi[3];

            //tekstitiedossa oli 1. sarakkeessa havaintokategoria
            kategoria = rivi[0];

            //kokeillaan, mitä tulee pyytämällä merkkijonoon *aika* liittyvää havaintolistaa
            havaintolista = palautus.get(aika);

            //jos havaintolistaa ko. aikavälille ei luotu, luodaan ja lisätään havainto 
            if (havaintolista == null) {
                havaintolista = new ArrayList();
                havaintolista.add(kategoria);
            } //jos aikaväliä varten on jo havaintolista, lisätään havainto
            else {
                havaintolista.add(kategoria);
            }
            palautus.put(aika, havaintolista);
        }
        return palautus;
    }

    private static HashMap<Ote, List<HavaintoTyyppi>> luoOtteet(HashMap<String, List<String>> m, Tallenne t) {

        HashMap<Ote, List<HavaintoTyyppi>> palautus = new HashMap();
        List<String> htyypit;
        List<HavaintoTyyppi> joku = new ArrayList();
        Ote ote;
        String[] ajat;
        Double alku, loppu;

        for (String s : m.keySet()) {
            ajat = s.split("-");
            alku = Double.parseDouble(ajat[0]);
            loppu = Double.parseDouble(ajat[1]);

            htyypit = m.get(s);

            ote = new Ote(t, alku, loppu);

            for (String z : htyypit) {
                joku.add(new HavaintoTyyppi(z));
                System.out.println(z);
            }
            palautus.put(ote, joku);
        }
        return palautus;
    }

    public static List<Double> listaaAlut(HashMap<String, List<String>> otteet) {
        return listaaAjat(otteet, 0);
    }

    public static List<Double> listaaLoput(HashMap<String, List<String>> otteet) {
        return listaaAjat(otteet, 1);
    }

    private static List<Double> listaaAjat(HashMap<String, List<String>> otteet, int nro) {

        String[] ajat;
        List<Double> palautus = new ArrayList();

        for (String o : otteet.keySet()) {

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

        List<String> rivit = lueTekstitiedosto(tallenne);

        HashMap<Ote, List<HavaintoTyyppi>> kokeilu = tuo(polku);

        System.out.println(kokeilu.get(args));

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
