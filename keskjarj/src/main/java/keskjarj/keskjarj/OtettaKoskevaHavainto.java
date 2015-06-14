/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.keskjarj;

import java.util.TreeSet;




/**
 * Otetta koskeva havainto on ajateltu ikään kuin otetta koskevaksi 
 * "binäärikseksi" havainnoksi. Se on joko totta, tai ei ole totta. Havaintoja 
 * voisi tehdä myös osanottajasta, tai kuulumisesta johonkin toisensa 
 * poissulkevista vaihtoehtoisista kategorioista, esimerkiksi. Muita mahdollisuuksia 
 * ei vaan (toistaiseksi) ole toteutettu.
 * @author mkahri
 */
public class OtettaKoskevaHavainto extends Havainto {



    public OtettaKoskevaHavainto(String nimi) {
        
        this.nimi = nimi;
        otteet = new TreeSet();
    }
}
