/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.keskjarj;

import java.util.HashSet;




/**
 * 
 * @author mkahri
 */
public class OtettaKoskevaHavainto extends Havainto {



    public OtettaKoskevaHavainto(String nimi) {
        
        this.nimi = nimi;
        otteet = new HashSet();
    }
}
