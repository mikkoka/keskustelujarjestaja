/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package keskjarj.keskjarj;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mikko
 */
public class TallenneTest
{
    Path polku;
    Tallenne tallenne;
    
    @Before
    public void setUp()
    {
        polku = Paths.get("/home/mikko/keskustelujarjestaja/aineistoja/Example.mp4");
        tallenne = new Tallenne(polku);
    }
    
    @Test
    public void SetPolkuEiAsetaTiedostoaJotaEiOle()
    {
        assertEquals(tallenne.setPolku(Paths.get("hehe")), false);
    }
    
    @Test
    public void SetPolkuAsettaaTiedostonJokaOnOlemassa()
    {
        Path polku2 = Paths.get("/home/mikko/keskustelujarjestaja/aineistoja/ElanExample.txt");
        assertEquals(tallenne.setPolku(polku2), true);
    }
    
//    @Test
//    public void SetTiedostonimiAsettaaTiedostonNimenOikein()
//    {
//        tallenne.setTiedostoNimi()
//        
//        Path polku2 = tallenne.getPolku();
//        
//        assertEquals(tallenne.setPolku(polku2), true);
//    }
}
