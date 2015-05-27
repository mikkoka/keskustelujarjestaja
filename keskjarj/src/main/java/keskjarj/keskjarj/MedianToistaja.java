/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package keskjarj.keskjarj;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Luokkaa käytetään mediatiedostojen toistamiseen VLC -mediaplayerilla.
 * Vaihtoehtoinen lähestymistapa vlcj, http://capricasoftware.co.uk/#/projects/vlcj
 */

public class MedianToistaja
{
    private Path VLCPolku;
    
    public MedianToistaja() {
        if (System.getProperty("os.name").equalsIgnoreCase("linux")) 
        {
            VLCPolku = Paths.get("vlc");            
        } 
        else paikannaVLC();
    }
    
    private boolean paikannaVLC() {
        setVLCPolku (Paths.get("vlc"));
        return true;
    }

    /**
     * Palauttaa VLC -mediaplayerin polun, sikäli kun sellainen on asetettu
     * @return Polku VLC -mediaplayeriin
     */
    public Path getVLCPolku () {
        return VLCPolku;
    }

    /**
     * Ottaa argumenttina polun VLC -mediaplayeriin,
     * sikäli kun sellaista tarvitaan (esim. Windows -järjestelmissä)
     * @param VLCPolku
     */
    public void setVLCPolku (Path VLCPolku) {
        this.VLCPolku = VLCPolku;
    }
    
    /**
     * Toistaa mediatiedoston VLC -mediaplayerilla.
     * Ottaa argumenttina polun toistettavaan otteeseen.
     * @param ote
     */
    public void toista (Ote ote) {
        tarkastaOte(ote);
        if (!kutsuVLC(ote))
            if (paikannaVLC())
                kutsuVLC(ote);
    }

    private String luoKomento (Ote ote) {
        return String.format("%s --play-and-stop %s --start-time %.3f "
                + "--stop-time %.3f", VLCPolku.toString(),
                ote.getTallenne().getPolku().toString(),
                ote.getAlku(),
                ote.getLoppu());
    }

    private boolean kutsuVLC (Ote ote){
        String komento = luoKomento(ote);
        try {
            Runtime.getRuntime().exec(komento);
        } 
        catch (IOException ex) {
            System.out.println(ex);
            System.err.println("Tarkasta VLC -mediaplayerin polku!");
            return false;
        }
        return true;
    }
    
    private boolean tarkastaOte (Ote ote) {
        return !(Objects.equals(ote.getAlku(), ote.getLoppu())); 
    }
            
    
    public static void main(String[] args) {
        MedianToistaja mt = new MedianToistaja();

        Path p = Paths.get("");
        Path pa = p.toAbsolutePath();
        System.out.println(pa); ///home/ad/fshome4/u4/m/mkahri/Documents/Ohjelmointi/keskustelujarjestaja/keskjarj
        Path polku = Paths.get("../aineistoja/Example.mp4");
        Tallenne tallenne = new Tallenne(polku);
        Ote ote = new Ote(tallenne, 10.0, 20.0);
        mt.toista(ote);
        System.out.println(System.getProperty("os.name").toLowerCase());
    }
}

