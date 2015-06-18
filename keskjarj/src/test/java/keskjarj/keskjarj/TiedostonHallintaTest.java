/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.keskjarj;

import keskjarj.ohjelma.AnnotaatioidenTuoja;
import java.nio.file.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mikko
 */
public class TiedostonHallintaTest {
    AnnotaatioidenTuoja tuoja;
    
    @Before
    public void setUp() {
    }

    @Test
    public void testTulostaTekstitiedosto() {
      assertEquals(tuoja.tulostaTekstitiedosto(Paths.get("../aineistoja/ElanExample.txt")), true);
    }
    
}
