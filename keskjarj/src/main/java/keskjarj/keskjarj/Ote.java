/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package keskjarj.keskjarj;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author mkahri
 */
public class Ote
{
    private Tallenne tallenne;
    private Osanottaja[] osanottajat;
    private Double alku, loppu;
    
    public Ote (Tallenne tallenne, Double alku, Double loppu)
    {
        this.tallenne = tallenne;
        
        if (alku < loppu && alku > 0)
        {
            this.alku = alku;
            this.loppu = loppu;
        } else 
        {   this.alku = 0.0;
            this.loppu = 0.0;
        }
    }
    
    public Tallenne getTallenne ()
    {
        return this.tallenne;
    }
    
    public void setTallenne(Tallenne tallenne) 
    {
        this.tallenne = tallenne;
    }
    
    public Double getAlku ()
    {
        return this.alku;
    }
    
    public Double getLoppu()
    {
        return this.loppu;
    }
        
    public void setAlku (Double alku)
    {
        if (alku < this.loppu)
            this.alku = alku;
    }
    
    public void setLoppu(Double loppu)
    {
        if (loppu > this.alku)
            this.loppu = loppu;
    }
    
    @Override
    public int hashCode() 
    {
        int luku1 = alku.hashCode();
        int luku2 = loppu.hashCode();
        
        return luku1 + luku2; //Emmätiiä, aattelin, jos tää ois aika ainutkertainen
    }

    @Override
    public boolean equals(Object toinen) 
    {
        if (toinen == null)
        {
            return false;
        }

        if (getClass() != toinen.getClass()) 
        {
            return false;
        }
        
        Ote ote2 = (Ote) toinen;
        
        if (!Objects.equals(this.alku, ote2.alku)) 
        {
            return false;
        }
        
        if (!Objects.equals(this.loppu, ote2.loppu)) 
        {
            return false;
        }
        
        return true;
    } 
}
