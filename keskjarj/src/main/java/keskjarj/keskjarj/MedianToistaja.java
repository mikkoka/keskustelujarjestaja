/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.keskjarj;

import java.io.IOException;

/**
 * Luokkaa käytetään mediatiedostojen toistamiseen VLC -mediaplayerilla.
 * Vaihtoehtoinen lähestymistapa vlcj, http://capricasoftware.co.uk/#/projects/vlcj
 */

public class MedianToistaja 
{
    private String VLCPolku = "";
    
    /**
     * Palauttaa VLC -mediaplayerin polun, sikäli kun sellainen on asetettu
     * @return Polku VLC mediaplayeriin 
     */
    public String getVLCPolku ()
    {
        return this.VLCPolku;
    }
    
    
     /**
     * Ottaa argumenttina polun VLC -mediaplayeriin, 
     * sikäli kun sellaista tarvitaan (esim. Windows -järjestelmissä)
     * @param VLCPolku
     */
    public void setVLCPolku (String VLCPolku)
    {
        this.VLCPolku = VLCPolku;
    }
     
    /**
     * Toistaa mediatiedoston VLC -mediaplayerilla. 
     * Ottaa argumentteina mediatiedoston polun, nimen sekä 
     * toiston alku- ja loppuajat sekunneissa. 
     * @param tiedostoPolku
     * @param tiedostoNimi
     * @param in
     * @param out
     * @return onnistuiko toisto
     */        
    public boolean toista (String tiedostoPolku, String tiedostoNimi, int in, int out) 
    {
        char viimeinenMerkki = tiedostoPolku.charAt(tiedostoPolku.length() - 1);
        
        if (viimeinenMerkki != '/')
            tiedostoPolku = tiedostoPolku + "/";
        
        if (out - in <= 0)
            return false;
                
        String komento = String.format("%svlc --play-and-stop %s%s"
                + " --start-time %d --stop-time %d",
                VLCPolku, tiedostoPolku, tiedostoNimi, in, out); 
        try 
        {            
            Process p = Runtime.getRuntime().exec(komento);
        } 
        catch (IOException ex) 
        {
            System.out.println(ex);
            return false;
        }
        return true;
    }
    
    public static void main (String[] args) 
    {
        MedianToistaja mt = new MedianToistaja();
        mt.toista("/home/mikko/Työpöytä/", "C20x.mpg", 10, 20);
        System.out.println();
    }  
}
