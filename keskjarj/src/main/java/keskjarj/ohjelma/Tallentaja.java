/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.ohjelma;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeSet;
import keskjarj.keskjarj.Havainto;
import keskjarj.keskjarj.Ote;
import keskjarj.keskjarj.Projekti;

/**
 * Luokasta luodaan instanssi, joka tallentaa projektin havainnot, otteet ja 
 * mediatiedostojen tiedot tekstitiedostoon. Luokan kenttiä ei voi muokata; tarkoitus
 * on, että luokan ilmentymää käytetään ainoastaan yhden kerran. 
 * @author mikko
 */
public class Tallentaja {
    
    private Projekti projekti;
    private TreeSet<Havainto> havainnot;
    private ArrayList<String> tekstirivit;


    public Tallentaja(Projekti projekti) {
        this.projekti = projekti;
        this.havainnot = projekti.getHavainnot();
        this.tekstirivit = new ArrayList();
    }
    
    /**
     * Tallentaa projektin havainnot ja otteet tekstitiedostoihin, jotka ovat 
     * muuten samanrakenteisia kuin Elan -ohjelman exporttaamat tekstitiedostot, 
     * paitsi että niihin on lisätty väliin polkuja mediatiedostoihin. Tämä 
     * mahdollistaa projektin tietojen tallentamisen ja lataamisen käyttäen 
     * pääosin ainoastaan luokan AnnotaatioidenTuoja metodeja.
     * 
     * @param polku polku, johon tiedot tallennetaan
     * @return tallennuksen onnistuminen
     */
    public boolean tallenna (Path polku)
    {
        if (!this.tekstirivit.isEmpty())
            return false;
        luoTekstirivit();
        return TiedostonHallinta.tallennaRivit(tekstirivit, polku);
    }
    
    /**
     * Luo tallennettavat tekstirivit lukemalla projektin havainnot yksi kerrallaan,
     * ja lisäämällä aina toisinaan väliin tekstirivin, jolla on mediatiedoston nimi.
     */
    private void luoTekstirivit() {  

        Path polku = projekti.getHavainto(0).getOte(0).getTallenne().getPolku();        
        tekstirivit.add(luoMediatiedostorivi(polku));
        Path temp;
        for (Havainto h : havainnot) {
            for (Ote o : h.getOtteet()) {
                temp = o.getTallenne().getPolku();
                if (!temp.equals(polku)) {
                    tekstirivit.add(luoMediatiedostorivi(temp));
                    polku = temp;
                }
                tekstirivit.add(luoRivi(h.getNimi(), puraAikajakso(o.getAjat(), 0), puraAikajakso(o.getAjat(), 1), o.getTunnus())); 
            }
        }
    }
    
     /**
     * Luo tekstirivin, jonka rakenne on periaatteessa samanlainen kuin Elanista
     * exportatun annotaatioita sisältävän tekstitiedoston rivi
     */
    private String luoRivi(String a, String b, String c, String d) {
        return String.format("%s\t\t%s\t%s\t%s", a, b, c, d);
    }
    
     /**
     * Luo tekstirivin, jolla on mediatiedoston polku suhteessa kansioon, 
     * josta käsin toimitaan
     */
    private String luoMediatiedostorivi (Path polku) {
       return "---->|" + (Paths.get(System.getProperty("user.dir"))).relativize(polku.toAbsolutePath()).toString();
    }
    
    private String puraAikajakso (String jakso, int jarjestysNro)
    {
       return jakso.split("-")[jarjestysNro];
    }
}
