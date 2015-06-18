/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package keskjarj.tieto;

import keskjarj.tieto.Tallenne;
import keskjarj.ohjelma.AnnotaatioidenTuoja;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.TreeSet;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mikko
 */
public class AnnotaatioidenTuojaTest {
    AnnotaatioidenTuoja tuoja;
    Tallenne tallenne;
    
    @Before
    public void setUp() {
        tallenne = new Tallenne (Paths.get("../aineistoja/p3adjsame.mp4"));
        tuoja = new AnnotaatioidenTuoja(Paths.get("../aineistoja/Elan_p3adjsame.txt"), tallenne);
    }
    
    @Test
    public void testTuoJotainEsimerkkitiedostolla() {
        boolean tuoJotain = tuoja.tuo().size() > 0;
        assertEquals(tuoJotain, true);
    }
    
    @Test
    public void testTuoTreeSetin() {   
        TreeSet koe = new TreeSet<>();
        assertEquals(tuoja.tuo().getClass(), koe.getClass());
    }
    
}
