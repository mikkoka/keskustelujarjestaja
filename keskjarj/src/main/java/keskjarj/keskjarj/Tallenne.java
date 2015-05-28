/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keskjarj.keskjarj;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 *
 * @author mkahri
 */
public class Tallenne {
    private Projekti projekti;
    private Path polku; 
    private Tallenne edellinen, seuraava;
    private ArrayList<HavaintoTyyppi> havaintoTyypit;
    
    public Tallenne (Path polku) 
    {
        if(Files.isReadable(polku))
        this.polku = polku;
    }
    
    public void setEdellinen (Tallenne edellinen)
    {
        this.edellinen = edellinen;
    }
    
    public Tallenne getEdellinen ()
    {
        return edellinen;
    }
    
        public void setSeuraava (Tallenne seuraava)
    {
        this.seuraava = seuraava;
    }
    
    public Tallenne getSeuraava ()
    {
        return seuraava;
    }
    
    public boolean setPolku (Path polku)
    {
        if(Files.notExists(polku))
            return false;
        else
            this.polku = polku;
        return true;
    }
    
    public Path getPolku ()
    {
        return this.polku;
    }
    
    public String getTiedostoNimi()
    {
        return polku.getFileName().toString();   
    }
    
//
//    
//    public static void main (String[] args )
//    {
//        Path p = Paths.get("/home/mikko/keskustelujarjestaja/aineistoja/Example.mp4");
//        Tallenne tallenne = new Tallenne(p);
//        System.out.println(tallenne.getPolku().subpath(0, tallenne.polku.getNameCount() - 1));
//        System.out.println(tallenne.getPolku());
//        System.out.println(tallenne.getTiedostoNimi());
//        System.out.println(tallenne.setPolku(Paths.get("hehe")));
//        System.out.println(tallenne.setPolku(p));
//        
//    }
}           
