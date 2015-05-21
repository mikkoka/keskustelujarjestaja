/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.keskjarj;

import java.nio.file.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mikko
 */
public class MedianToistajaTest 
{
    MedianToistaja mt;
    Path polku;
    Tallenne tallenne;
    Ote ote;
       
    @Before
    public void setUp() 
    {
        mt = new MedianToistaja();
        polku = Paths.get("/home/mikko/keskustelujarjestaja/aineistoja/Example.mp4");
        tallenne = new Tallenne(polku);
        ote = new Ote(tallenne, 10.0, 20.0);    
    }
    
    @Test
    public void testToista() 
    {
        assertEquals(mt.toista(ote), true);
    }
    
        @Test
    public void testEiToistaJosToistoajatPuuttuvat() 
    {
        ote = new Ote(tallenne, 20.0, 10.0);
        assertEquals(mt.toista(ote), false);
    }

    
}
