/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.keskjarj;

import java.util.HashSet;
import java.util.Objects;



/**
 * 
 * @author mkahri
 */
public class OtettaKoskevaHavainto extends Havainto {



    public OtettaKoskevaHavainto(String nimi) {
        
        this.nimi = nimi;
        otteet = new HashSet();
    }

    @Override
    public String getNimi() {        
        return this.nimi;
    }

    @Override
    public void setNimi(String nimi) {        
        this.nimi = nimi;
    }

    @Override
    public HashSet<Ote> getOtteet() {        
        return otteet;
    }

    @Override
    public void lisaaOte(Ote ote) {        
        otteet.add(ote);
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

        OtettaKoskevaHavainto havainto2 = (OtettaKoskevaHavainto) toinen;

        if (!Objects.equals(this.nimi, havainto2.nimi)) {
            return false;
        }
        
        return true;
    }
}
