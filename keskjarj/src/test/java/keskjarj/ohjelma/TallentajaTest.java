/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.ohjelma;

import java.nio.file.Path;
import java.nio.file.Paths;
import keskjarj.tieto.Projekti;
import keskjarj.tieto.Tallenne;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mikko
 */
public class TallentajaTest {
    Projekti projekti;
    AnnotaatioidenTuoja tuoja;
    Path polku1; Tallenne tallenne;
    
    public TallentajaTest() {
        polku1 = Paths.get("../aineistoja/Elan_p3adjsame.txt");
        tallenne = new Tallenne(Paths.get("../aineistoja/p3adjsame.mp4"));
        projekti = new Projekti();
        projekti.tuoAnnotaatioita(polku1, tallenne);
    }
    
    @Before
    public void setUp() {
    }
    
    /*
    * Tallentajan toimintaa on ihan mahoton testata kaikita osiltaan.
    * Alla oleva ei toimi, kun uudessa tiedostossa on samat rivit eri järjestyksessä
    * (ja joka rivillä yksi uusi sarake) plus uusia rivejä (joilla on mediatiedostojen nimiä)
    
    @Test
    public void testTallenna() {
        Tallentaja tallentaja = new Tallentaja(projekti);
        Path polku2 = Paths.get("../aineistoja/Elan_p3adjsameTEST.txt");
        tallentaja.tallenna(polku2);
        
        String rivi0 = TiedostonHallinta.tuoRivit(polku1).get(0).substring(0, 10);
        String rivi1 = TiedostonHallinta.tuoRivit(polku2).get(1).substring(0, 10);        
        assertEquals(rivi1, rivi0);

    }
    */
    
    
    @Test
    public void testSamaaInstanssiaEiVoiKayttaaKahtaKertaa() {
        Tallentaja tallentaja = new Tallentaja(projekti);
        Path polku2 = Paths.get("../aineistoja/Elan_p3adjsameTEST.txt");
        tallentaja.tallenna(polku2);
             
        assertEquals(tallentaja.tallenna(polku2), false);

    }
    
}
