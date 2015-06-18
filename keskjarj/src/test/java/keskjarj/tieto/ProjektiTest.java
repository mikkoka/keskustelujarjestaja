/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.tieto;

import keskjarj.tieto.Tallenne;
import keskjarj.tieto.Havainto;
import keskjarj.tieto.Ote;
import keskjarj.tieto.Projekti;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mikko
 */
public class ProjektiTest {
    Projekti projekti;
    Tallenne tallenne;
    
    public ProjektiTest() {
    }
        
    @Before
    public void setUp() {
        projekti = new Projekti();
        tallenne = new Tallenne (Paths.get("../aineistoja/p3adjsame.mp4"));
    }

    /**
     * Test of tuoAnnotaatioita method, of class Projekti.
     */
    @Test
    public void testTuoAnnotaatioitaKelvottomallaPolulla() {
        Path polku = Paths.get("liirumlaarum");
        assertEquals(false, projekti.tuoAnnotaatioita(polku, tallenne));
    }
    
    @Test
    public void testTuoAnnotaatioitaKunnollisellaTiedostolla() {
        Path polku = Paths.get("../aineistoja/Elan_p3adjsame.txt");
        assertEquals(true, projekti.tuoAnnotaatioita(polku, tallenne));
    }
    
    @Test
    public void testUudenHavaintoKategorianLuominen() {
        assertEquals(true, projekti.luoHaivaintokategoria("testinimi"));
    }    
    
    @Test
    public void testSamanHavaintoKategorianUudelleenLuominen() {
        projekti.luoHaivaintokategoria("testinimi");
        assertEquals(false, projekti.luoHaivaintokategoria("testinimi"));
    }
    
    @Test
    public void havaintoPalauttaaNullinLiianSuurellaHavaintonumerolla() {
        
        assertEquals(null, projekti.getHavainto(1000));
    }
    
    @Test
    public void otePalauttaaNullinLiianSuurellaOtenumerolla() {
        
        assertEquals(null, projekti.getProjektinOte(1000));
    }
    
    @Test
    public void otePalauttaaNullinTyhjallaHavaintoluettelolla() {
        Projekti projekti2 = new Projekti();
        assertEquals(null, projekti2.getProjektinOte(0));
    }
    
    @Test
    public void havaintoPalauttaaNullinTyhjallaHavaintoluettelolla() {
        Projekti projekti2 = new Projekti();
        assertEquals(null, projekti2.getHavainto(0));
    }
        
        
    @Test
    public void kaikkiOtteetPalauttaaNullinTyhjallaHavaintoluettelolla() {
        Projekti projekti2 = new Projekti();
        assertEquals(true, projekti.getOtteet().isEmpty());
    }
    
    
    @Test
    public void testListaaHavaintoMerkkijonojenMaaranOikein () {
        Path polku = Paths.get("../aineistoja/Elan_p3adjsame.txt");
        projekti.tuoAnnotaatioita(polku, tallenne);
        assertEquals(projekti.getHavainnotString().length, projekti.getHavainnot().size());
    } 
    
    @Test
    public void testaaHavainnonOlemassaolo () {
        Path polku = Paths.get("../aineistoja/Elan_p3adjsame.txt");
        projekti.tuoAnnotaatioita(polku, tallenne);
        String nimi1 = projekti.getHavainto(0).nimi;
        assertEquals(projekti.havaintoOlemassa(nimi1), true);  
    } 
    
    @Test
    public void testaaHavainnonTuontiNimenPerusteella() {
        Path polku = Paths.get("../aineistoja/Elan_p3adjsame.txt");
        projekti.tuoAnnotaatioita(polku, tallenne);
        String nimi1 = projekti.getHavainto(0).nimi;
        Havainto h = projekti.getHavainto(nimi1);
        assertEquals(h.getNimi(), nimi1);

    }
    
        @Test
    public void testaaOtteenTuontiNimenPerusteella() {
        Path polku = Paths.get("../aineistoja/Elan_p3adjsame.txt");
        projekti.tuoAnnotaatioita(polku, tallenne);
        String nimi1 = projekti.getHavainto(0).getOte(0).getTunnus();
        Ote o = projekti.getProjektinOte(nimi1);
        assertEquals(o.getTunnus(), nimi1);

    }
    
    @Test
    public void testaaOtteenTuontiNumeronPerusteella() {
        Path polku = Paths.get("../aineistoja/Elan_p3adjsame.txt");
        projekti.tuoAnnotaatioita(polku, tallenne);
        Ote o1 = projekti.getProjektinOte(0);
        Ote o2 = projekti.getOtteet().first();
        assertEquals(o1, o2);

    }
    
    
    
    
//    @Test
//    public void testOtteet() {
//        projekti.tuoAnnotaatioita(Paths.get("../aineistoja/ElanExample.txt"), tallenne);
//        HashSet<Ote> otteet = projekti.otteet("Begin");
//        assertEquals(8, otteet.size());
//    }
    
    

    
}
