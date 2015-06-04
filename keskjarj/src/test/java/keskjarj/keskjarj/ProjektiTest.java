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
    
    public ProjektiTest() {
    }
        
    @Before
    public void setUp() {
        projekti = new Projekti();
    }

    /**
     * Test of tuoAnnotaatioita method, of class Projekti.
     */
    @Test
    public void testTuoAnnotaatioitaKelvottomallaPolulla() {
        Path polku = Paths.get("liirumlaarum");
        assertEquals(false, projekti.tuoAnnotaatioita(polku));
    }
    
    @Test
    public void testTuoAnnotaatioitaKunnollisellaTiedostolla() {
        Path polku = Paths.get("../aineistoja/ElanExample.txt");
        assertEquals(true, projekti.tuoAnnotaatioita(polku));
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
    public void testOtteet() {
        projekti.tuoAnnotaatioita(Paths.get("../aineistoja/ElanExample.txt"));
        HashSet<Ote> otteet = projekti.otteet("Begin");
        assertEquals(8, otteet.size());
    }
    
    

    
}
