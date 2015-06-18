/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.ohjelma;

import java.nio.file.*;
import keskjarj.tieto.Ote;
import keskjarj.tieto.Tallenne;
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
        polku = Paths.get("../aineistoja/p3adjsame.mp4");
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
}
