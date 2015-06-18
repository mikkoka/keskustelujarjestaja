/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.ohjelma;

import keskjarj.tieto.Tallenne;
import keskjarj.tieto.Projekti;
import java.nio.file.*;
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mikko
 */
public class LataajaTest {
    
    Projekti projekti;
    AnnotaatioidenTuoja tuoja;
    Path polku1, polku2; Tallenne tallenne;
    Lataaja lataaja;
    
    public LataajaTest() {
        polku1 = Paths.get("../aineistoja/Elan_p3adjsameTEST.keskjarj");
        polku2 = Paths.get("../aineistoja/p3adjsame.mp4");
        tallenne = new Tallenne(polku2);
        projekti = new Projekti();
        //projekti.tuoAnnotaatioita(polku1, tallenne);
        
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testLataaToimivastaPolusta() {
        assertEquals(Lataaja.lataa(polku1, projekti), true);
    }
    
    @Test
    public void testEiLataaToimimattomastaPolusta() {
        assertEquals(false, Lataaja.lataa(polku2, projekti));
    }
    
}
