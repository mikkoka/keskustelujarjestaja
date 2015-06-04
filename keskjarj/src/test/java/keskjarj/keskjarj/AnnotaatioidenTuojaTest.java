/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package keskjarj.keskjarj;

import java.nio.file.Paths;
import java.util.HashSet;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mikko
 */
public class AnnotaatioidenTuojaTest {
    AnnotaatioidenTuoja tuoja;
    
    @Before
    public void setUp() {
        tuoja = new AnnotaatioidenTuoja(Paths.get("../aineistoja/ElanExample.txt"));
    }
    
    @Test
    public void testTuoJotainEsimerkkitiedostolla() {
        boolean tuoJotain = tuoja.tuo().size() > 0;
        assertEquals(tuoJotain, true);
    }
    
    @Test
    public void testTuoHashSetin() {   
        HashSet koe = new HashSet<>();
        assertEquals(tuoja.tuo().getClass(), koe.getClass());
    }
    
}
