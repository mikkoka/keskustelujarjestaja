/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package keskjarj.keskjarj;

import java.awt.*;
import keskjarj.gui.*;

/**
 * Tämä on Keskustelunjärjestäjän pääluokka, josta ohjelman suoritus alkaa.
 * @author mkahri
 */
public class Keskustelujarjestaja {  

    public static void main(String[] args)
    {
        Projekti projekti = new Projekti(); 
        Dimension paneelinKoko = new Dimension(1024, 768);
        GUI gui = new GUI(projekti, paneelinKoko);
    }
}
