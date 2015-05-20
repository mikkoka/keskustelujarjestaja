/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.keskjarj;

import java.nio.file.Path;

/**
 *
 * @author mkahri
 */
public class Tallenne {
    private String tiedostoNimi, polku;
    private Path ppolku; 
    private Tallenne edellinen;
    
    public Tallenne (String tiedostoPolku, String tiedostoNimi)
    {
        this.tiedostoNimi = tiedostoNimi;
        this.polku = tiedostoPolku;
    }
    
    public void setEdellinen (Tallenne edellinen)
    {
        this.edellinen = edellinen;
    }
    
    public Tallenne getEdellinen ()
    {
        return edellinen;
    }
    
    public void setPolku (String polku)
    {
        this.polku = polku;
    }
    
    public String getPolku ()
    {
        return this.polku;
    }
    
    public void setTiedostoNimi(String nimi)
    {
        
    }
            
}
