/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.ohjelma;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.TreeSet;
import keskjarj.keskjarj.Havainto;
import keskjarj.keskjarj.Ote;
import keskjarj.keskjarj.Projekti;

/**
 *
 * @author mikko
 */
public class Tallentaja {
    
    Projekti projekti;
    TreeSet<Havainto> havainnot;
    ArrayList<String> teksti;
    TiedostonHallinta tiedostonhallinta;

    public Tallentaja(Projekti projekti) {
        this.projekti = projekti;
        this.havainnot = projekti.getHavainnot();
        this.teksti = new ArrayList();
        this.tiedostonhallinta = new TiedostonHallinta();
    }
    
    public boolean tallenna (Path polku)
    {
        luoTeksti();
        return tiedostonhallinta.tallennaRivit(teksti, polku);
    }

    private void luoTeksti() {

        Path polku = projekti.getHavainto(0).getOte(0).getTallenne().getPolku();
        teksti.add("---->|" + polku.toString());
        Path temp;
        for (Havainto h : havainnot) {
            for (Ote o : h.getOtteet()) {
                temp = o.getTallenne().getPolku();
                if (!temp.equals(polku)) {
                    teksti.add("---->|" + temp.toString());
                    polku = temp;
                }
                teksti.add(luoRivi(h.getNimi(), puraAikajakso(o.getAjat(), 0), puraAikajakso(o.getAjat(), 1), o.getTunnus())); 
            }
        }
    }
    
    private String luoRivi(String a, String b, String c, String d) {
        return String.format("%s\t\t%s\t%s\t%s", a, b, c, d);
    }
    
    private String puraAikajakso (String jakso, int jarjestysNro)
    {
       return jakso.split("-")[jarjestysNro];
    }
}
