/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package keskjarj.tieto;

import keskjarj.tieto.OtettaKoskevaHavainto;
import keskjarj.tieto.Tallenne;
import keskjarj.tieto.Ote;
import java.nio.file.Paths;
import java.util.TreeSet;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mikko
 */
public class HavaintoTest {
    OtettaKoskevaHavainto havainto;
    
    
    public HavaintoTest() {
        
    }
    
    @Before
    public void setUp() {
        havainto = new OtettaKoskevaHavainto("nimi");
    }
    
    @Test
    public void testaaSamanlaisenOtteenLisaaminen() {
        Tallenne tallenne = new Tallenne (Paths.get("../aineistoja/p3adjsame.mp4"));
        Ote ote1 = new Ote(tallenne, 10.0, 11.0, "10-11", "p3adjsame.mp4");
        Ote ote2 = new Ote(tallenne, 10.0, 11.0, "10-11", "p3adjsame.mp4");
        
        havainto.lisaaOte(ote1);
        assertEquals(havainto.lisaaOte(ote2), false);
        
    }
    
    @Test
    public void testaaErilaisenOtteenLisaaminen() {
        Tallenne tallenne = new Tallenne (Paths.get("../aineistoja/p3adjsame.mp4"));
        Ote ote1 = new Ote(tallenne, 10.0, 11.0, "10-11", "p3adjsame.mp4");
        Ote ote2 = new Ote(tallenne, 20.0, 21.0, "20-21", "p3adjsame.mp4");
        
        havainto.lisaaOte(ote1);
        assertEquals(havainto.lisaaOte(ote2), true);
        
    }
    
    @Test
    public void testaaLisaaOtteita() {
        Tallenne tallenne = new Tallenne (Paths.get("../aineistoja/p3adjsame.mp4"));
        Ote[] testiotteet = new Ote[3];
        testiotteet[0] = new Ote(tallenne, 10.0, 11.0, "10-11", "p3adjsame.mp4");
        testiotteet[1] = new Ote(tallenne, 20.0, 21.0, "11-12", "p3adjsame.mp4");
        testiotteet[2] = new Ote(tallenne, 30.0, 31.0, "12-13", "p3adjsame.mp4");
        
        havainto.lisaaOtteita(testiotteet);
        TreeSet<Ote> otteet = havainto.getOtteet();
        
        assertEquals(3, otteet.size());
        
    }
    
    @Test
    public void testaaHiukanEqualsia() {
       Tallenne tallenne = new Tallenne (Paths.get("../aineistoja/p3adjsame.mp4"));
       OtettaKoskevaHavainto havainto2 = new OtettaKoskevaHavainto("nimi"); 
        assertEquals(true, havainto.equals(havainto2));
        
    }
    
     @Test
    public void testaaHiukanEqualsia2() {
       Tallenne tallenne = new Tallenne (Paths.get("../aineistoja/p3adjsame.mp4"));
       OtettaKoskevaHavainto havainto2 = new OtettaKoskevaHavainto("nim"); 
        assertEquals(false, havainto.equals(havainto2));
        
    }
    
    @Test
    public void testaaSisaltaaSamojaOtteita_metodia() {
       Tallenne tallenne = new Tallenne (Paths.get("../aineistoja/p3adjsame.mp4"));
       Ote testiote1 = new Ote(tallenne, 10.0, 11.0, "10-11", "p3adjsame.mp4");
       Ote testiote2 = new Ote(tallenne, 20.0, 21.0, "11-12", "p3adjsame.mp4");
       OtettaKoskevaHavainto havainto2 = new OtettaKoskevaHavainto("nim"); 
       havainto.lisaaOte(testiote1);
       havainto.lisaaOte(testiote2);
       havainto2.lisaaOte(testiote2);
       assertEquals(true, havainto.sisaltaaSamojaOtteita(havainto2));
        
    }
    
    
    
}
