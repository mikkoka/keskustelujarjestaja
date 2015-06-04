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
        Path polku = Paths.get("../aineistoja/Example.mp4");
        tallenne = new Tallenne(polku);
        ote = new Ote(tallenne, 10.0, 20.0, "11-12", "joku.txt");
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
        Ote ote2 = new Ote(tallenne, 20.0, 10.0, "11-12", "joku.txt");
        Double testiLuku = 0.0;
        assertEquals(ote2.getAlku(), testiLuku);  
    }
    
    @Test
    public void KonstruktoriEiAsetaAlkuaJokaAlleNollan ()
    {
        Ote o = new Ote(tallenne, -1.0, 10.0, "11-12", "joku.txt");
        Double testiLuku = 0.0;
        assertEquals(o.getAlku(), testiLuku);  
    }
    
    @Test
    public void testGetTunnusAsetetullaTunnuksella()
    {
        ote.setTunnus("kokeilu");
        assertEquals("kokeilu", ote.getTunnus());
    }
    
    @Test
    public void testGetTunnusAIlmanTunnuksenAsettamista()
    {
        assertEquals("joku.txt 11-12", ote.getTunnus());
    }
    
    @Test
    public void testaaHiukanEqualsia() {
       Ote ote2 = new Ote(tallenne, 10.0, 20.0, "11-12", "joku.txt");
       assertEquals(true, ote.equals(ote2));
        
    }
    
    @Test
    public void testaaHiukanEqualsia2() {
       Ote ote2 = new Ote(tallenne, 1.0, 11.0, "11-13", "joku.txt");
       assertEquals(false, ote.equals(ote2));
        
    }
    
    
        
        
}
