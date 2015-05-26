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
        if (System.getProperty("os.name").equalsIgnoreCase("linux")) {
            this.VLCPolku = Paths.get("vlc");            
        } else this.VLCPolku = this.findVLC();
    }
    
    private Path findVLC()
    {
        return Paths.get("vlc"); 
    }

    /**
     * Palauttaa VLC -mediaplayerin polun, sikäli kun sellainen on asetettu
     * @return Polku VLC -mediaplayeriin
     */
    public Path getVLCPolku ()
    {
        return this.VLCPolku;
    }
    
    
    /**
     * Ottaa argumenttina polun VLC -mediaplayeriin,
     * sikäli kun sellaista tarvitaan (esim. Windows -järjestelmissä)
     * @param VLCPolku
     */
    public void setVLCPolku (Path VLCPolku)
    {
        this.VLCPolku = VLCPolku;
    }
    
    /**
     * Toistaa mediatiedoston VLC -mediaplayerilla.
     * Ottaa argumenttina polun toistettavaan otteeseen.
     * @param ote
     * @return onnistuiko komennon muodostaminen
     */
    public boolean toista (Ote ote)
    {
        if (Objects.equals(ote.getAlku(), ote.getLoppu()))
            return false;

        String komento = String.format("%sjeejee --play-and-stop %s --start-time %.3f "
                + "--stop-time %.3f", VLCPolku.toString(),
                ote.getTallenne().getPolku().toString(),
                ote.getAlku(),
                ote.getLoppu());
        boolean valmis = false;
        do
        {
            try {
                Process p = Runtime.getRuntime().exec(komento);
                valmis = true;
            } catch (IOException ex) {
                System.out.println(ex);
                if (ex.getMessage().contains("error=2")) {
                    System.out.println(this.findVLC());
                    valmis = true;
                }

                return false;
            }
        } while (!valmis); 
        return true;
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

