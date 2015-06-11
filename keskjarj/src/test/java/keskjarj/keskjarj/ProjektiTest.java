/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.keskjarj;

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
        tallenne = new Tallenne (Paths.get("../aineistoja/Example.mp4"));
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
        Path polku = Paths.get("../aineistoja/ElanExample.txt");
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
        
        assertEquals(null, projekti.havainto(1000));
    }
    
    @Test
    public void otePalauttaaNullinLiianSuurellaOtenumerolla() {
        
        assertEquals(null, projekti.ote(1000));
    }
    
    @Test
    public void otePalauttaaNullinTyhjallaHavaintoluettelolla() {
        Projekti projekti2 = new Projekti();
        assertEquals(null, projekti2.ote(0));
    }
    
    @Test
    public void havaintoPalauttaaNullinTyhjallaHavaintoluettelolla() {
        Projekti projekti2 = new Projekti();
        assertEquals(null, projekti2.havainto(0));
    }
        
        
    @Test
    public void kaikkiOtteetPalauttaaNullinTyhjallaHavaintoluettelolla() {
        Projekti projekti2 = new Projekti();
        assertEquals(true, projekti.kaikkiOtteet().isEmpty());
    }
    
//    @Test
//    public void testOtteet() {
//        projekti.tuoAnnotaatioita(Paths.get("../aineistoja/ElanExample.txt"), tallenne);
//        HashSet<Ote> otteet = projekti.otteet("Begin");
//        assertEquals(8, otteet.size());
//    }
    
    

    
}
