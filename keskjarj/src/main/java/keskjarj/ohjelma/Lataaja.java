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
import keskjarj.tieto.Projekti;
import keskjarj.tieto.Tallenne;

/**
 * Luokan staattista metodia lataa() käytetään luokan Tallentaja metodilla tallenna()
 * tallennettujen tietojen lukemiseen.
 * @author mikko
 */
public class Lataaja
{

    /**
     * Lataa tallennetut otteet ja havainnot luokan Tallentaja metodilla tallenna()
     * tallennetusta tekstitiedostosta. Lataaminen todennäköisesti epäonnoistuu,
     * jos tallennus on suoritettu eri koneella, kuin millä lataaminen tehdään, JA
     * on toisaalta käytetty mediatiedostoja, jotka sijaitsevat muualla kuin 
     * kansion keskustelujarjestaja alikansioissa. Ongelmatilanteen voi pakottavan
     * tarpeen sitä vaatiessa korjata muokkaamalla käsin polkuja tallennetussa 
     * tekstitiedostossa.
     * 
     * Tallentajan tallenna() -metodi tallentaa projektin havainnot ja otteet 
     * tekstitiedostoihin, jotka ovat muuten samanlaisia kuin Elan -ohjelman
     * exporttaamat tekstitiedostot, paitsi että niihin on lisätty väliin
     * polkuja mediatiedostoihin. Metodi lataa() "pätkii" tallennetun tekstitiedoston
     * pätkiin aina mediatiedoston nimen kohdalta, ja kutsuu jokaista pätkää koden
     * luokan AnnotaatioidenTuoja metodia tuo(), mediatiedoston nimen perusteella 
     * luotu Tallenne -luokan instanssi mukanaan.
     * 
     * Ongelma, että lataaminen epäonnistuu jos tallennuskone on eri kuin latauskone
     * ja polku mediatiedostoon toisaalta monimutkaisempi, johtuu siitä, että luokan
     * Tallenne konstruktori tarkastaa mediatiedoston polun luettavuutta ennen sen 
     * hyväksymistä. 
     * 
     * 
     * @param polku polku tekstitiedostoon, joka ladataan
     * @param projekti projekti, johon tiedot tuodaan
     */
    public static boolean lataa(Path polku, Projekti projekti)
    {
        List<String> osa; //
        List<String> listarivit = TiedostonHallinta.tuoRivit(polku);
        if (listarivit == null || listarivit.isEmpty())
            return false;
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
            if (laskuri < rivit.length) {
                System.out.println(rivit[laskuri].substring(6));
                tallenne = new Tallenne(Paths.get(rivit[laskuri].substring(6)));
            }
            laskuri++;
        } 
        return true;
    }   
}
