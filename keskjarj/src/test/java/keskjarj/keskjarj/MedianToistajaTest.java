/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.keskjarj;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mkahri
 */
public class MedianToistajaTest {
    
    
    @Before
    public void setUp() {
    }
    
    /**
     * Test of getVLCPolku method, of class MedianToistaja.
     */
    @Test
    public void testGetVLCPolku() {
        System.out.println("getVLCPolku");
        MedianToistaja instance = new MedianToistaja();
        String expResult = "";
        String result = instance.getVLCPolku();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVLCPolku method, of class MedianToistaja.
     */
    @Test
    public void testSetVLCPolku() {
        System.out.println("setVLCPolku");
        String VLCPolku = "";
        MedianToistaja instance = new MedianToistaja();
        instance.setVLCPolku(VLCPolku);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toista method, of class MedianToistaja.
     */
    @Test
    public void testToista() {
        System.out.println("toista");
        String tiedostoPolku = "";
        String tiedostoNimi = "";
        int in = 0;
        int out = 0;
        MedianToistaja instance = new MedianToistaja();
        boolean expResult = false;
        boolean result = instance.toista(tiedostoPolku, tiedostoNimi, in, out);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class MedianToistaja.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        MedianToistaja.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
