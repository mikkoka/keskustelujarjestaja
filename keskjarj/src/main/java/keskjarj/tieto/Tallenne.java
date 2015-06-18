/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.tieto;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Hallinnoi ääni- ja videotallenteiden tietoja, ja tietoja niitten 
 * keskinäisistä suhteista. 
 */
public class Tallenne {
    private Path polku; 
    private Tallenne edellinen, seuraava;
   
    /**
     * Konstruktori tarkastaa parametrinä annetun, mediatiedostoon vievän polun 
     * luettavuuden ja luo luettavalle tiedostolle tallenteen. Luettavuuden 
     * tarkistus ei valitettavasti varmista, että ko. tiedosto on toistettavissa 
     * oleva *mediatiedosto*, vaan ainoastaan, että se on olemassa ja luettavissa.
     * @param polku polku mediatiedostoon 
     */
    public Tallenne (Path polku) 
    {
        if(Files.isReadable(polku))
        this.polku = polku;
    }
    
    /**
     * Tämä ominaisuus poistettaneen kiireen vuoksi.
     * @param edellinen sarjan edellinen tallenne
     */
    public void setEdellinen (Tallenne edellinen)
    {
        this.edellinen = edellinen;
    }
    
    /**
     * Tämä ominaisuus poistettaneen kiireen vuoksi.
     * @return sarjan edellinen tallenne
     */
    public Tallenne getEdellinen ()
    {
        return edellinen;
    }
    
    /**
     * Tämä ominaisuus poistettaneen kiireen vuoksi.
     * @param seuraava sarjan seuraava tallenne
     */
    public void setSeuraava (Tallenne seuraava)
    {
        this.seuraava = seuraava;
    }
    
    /**
     * Tämä ominaisuus poistettaneen kiireen vuoksi.
     * @return sarjan seuraava tallenne
     */
    public Tallenne getSeuraava ()
    {
        return seuraava;
    }
    
    /**
     * Asettaa polun mediatiedostoon, mikäli polku luettavissa. Luettavuuden 
     * tarkistus ei valitettavasti varmista, että ko. tiedosto on toistettavissa 
     * oleva *mediatiedosto*, vaan ainoastaan, että se on olemassa ja luettavissa.
     * @param polku polku mediatiedostoon 
     * @return
     */
    public boolean setPolku (Path polku)
    {
        if(Files.notExists(polku))
            return false;
        else
            this.polku = polku;
        return true;
    }
    
    /**
     * Palauttaa polun mediatiedostoon.
     * @return polku mediatiedostoon 
     */
    public Path getPolku ()
    {
        return this.polku;
    }
    
    /**
     * Palauttaa tiedoston nimen ilman polkua.
     * @return tiedoston nimi merkkijonona
     */
    public String getTiedostoNimi()
    {
        return polku.getFileName().toString();
    }
}           
