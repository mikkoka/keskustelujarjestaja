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
public class OteTest
{
    Tallenne tallenne;
    Ote ote;
    
    public OteTest()
    {
    }
    
    @Before
    public void setUp()
    {
        Path polku = Paths.get("/home/mikko/keskustelujarjestaja/aineistoja/Example.mp4");
        tallenne = new Tallenne(polku);
        ote = new Ote(tallenne, 10.0, 20.0);
    }
    
    @Test
    public void testGetTallenne()
    {
        assertEquals(tallenne, ote.getTallenne());
    }
    
    @Test
    public void testSetTallenne()
    {
        Path polku2 = Paths.get("/home/mikko/MOCAP/Video2");
        Tallenne tallenne2 = new Tallenne(polku2);
        
        ote.setTallenne(tallenne2);
        
        assertEquals(tallenne2, ote.getTallenne());
    }
    
    @Test
    public void KonstruktoriEiAsetaAlkuaJokaSuurempiKuinLoppu ()
    {
        Ote ote2 = new Ote(tallenne, 20.0, 10.0);
        Double testiLuku = 0.0;
        assertEquals(ote2.getAlku(), testiLuku);  
    }
    
        public void KonstruktoriEiAsetaAlkuaJokaAlleNollan ()
    {
        Path polku = Paths.get("/home/mikko/keskustelujarjestaja/aineistoja/Example.mp4");
        Ote o = new Ote(tallenne, -1.0, 10.0);
        Double testiLuku = 0.0;
        assertEquals(o.getAlku(), testiLuku);  
    }
}
