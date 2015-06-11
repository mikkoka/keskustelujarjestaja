/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.ohjelma;

import keskjarj.ohjelma.MedianToistaja;
import java.nio.file.*;
import keskjarj.keskjarj.Ote;
import keskjarj.keskjarj.Tallenne;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author mikko
 */
public class MedianToistajaTest {

    MedianToistaja mt;
    Path polku;
    Tallenne tallenne;
    Ote ote;

    @Before
    public void setUp() {
        mt = new MedianToistaja();
        polku = Paths.get("../aineistoja/Example.mp4");
        tallenne = new Tallenne(polku);
        ote = new Ote(tallenne, 10.0, 20.0, "11-12", "joku.txt");
    }

    @Test
    public void testaaKutsuVLC() {
        assertEquals(mt.kutsuVLC(ote), true);
    }

    @Test
    public void testaaKutsuVLCnToimintaOudollaVLCPolulla() {
        mt.setVLCPolku(Paths.get("hehehe/hohoho"));
        assertEquals(mt.kutsuVLC(ote), false);
    }

    @Test
    public void testaaOteMoitteeton_ToistoEstyyJosToistoajatPuuttuvat() {
        ote = new Ote(tallenne, 20.0, 10.0, "11-12", "joku.txt");
        assertEquals(mt.oteMoitteeton(ote), false);
    }
    
        @Test
    public void testaaOteMoitteeton_ToistoEstyyJosPolkuKelvoton() {
        Path p = Paths.get("../aineistoja/Example.hehe");
        Tallenne t = new Tallenne(p);
        Ote o = new Ote(t, 10.0, 11.0, "11-12", "joku.txt");
        assertEquals(mt.oteMoitteeton(o), false);
    }

//    @Test
//    public void testaaEttaKomennossaOikeitaMerkkeja() {
//        String komento = mt.luoKomento(ote);
//        if (!komento.contains("vlc --play-and-stop ")) {
//            fail();
//        }
//        
//        assertEquals(mt.oteMoitteeton(ote), false);
//    }
}
