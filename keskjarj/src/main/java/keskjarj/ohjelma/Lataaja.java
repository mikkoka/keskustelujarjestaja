/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.ohjelma;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import keskjarj.keskjarj.Projekti;
import keskjarj.keskjarj.Tallenne;

/**
 *
 * @author mikko
 */
public class Lataaja
{

    public static void lataa(Path polku, Projekti projekti)
    {
        List<String> osa; 
        List<String> listarivit = TiedostonHallinta.tuoRivit(polku);
        String[] rivit = listarivit.toArray(new String[listarivit.size()]);

        
        int laskuri = 1;
        Tallenne tallenne = new Tallenne(Paths.get(rivit[0].substring(6)));
        
        while (laskuri < rivit.length) 
        {
            osa = new ArrayList();
            while (laskuri < rivit.length && !rivit[laskuri].startsWith("---->|")) 
            {
                osa.add(rivit[laskuri]);
                laskuri++;
            }
            projekti.tuoAnnotaatioita(osa, tallenne);
            if (laskuri < rivit.length)
                tallenne = new Tallenne(Paths.get(rivit[laskuri].substring(6)));
            laskuri++;
        }     
    }   
}
