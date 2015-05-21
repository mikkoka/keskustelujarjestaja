/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package keskjarj.keskjarj;

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
        } else {
            this.alku = 0.0;
            this.loppu = 0.0;
        }
    }
    
    public Tallenne getTallenne ()
    {
        return this.tallenne;
    }
    
    public Double getAlku ()
    {
        return this.alku;
    }
    
    public Double getLoppu()
    {
        return this.loppu;
    }
    
    public void setTallenne (Tallenne tallenne)
    {
        this.tallenne = tallenne;
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
}
