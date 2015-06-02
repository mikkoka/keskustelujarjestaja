/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.keskjarj;

import java.util.HashSet;

/**
 *
 * @author mikko
 */
public abstract class Havainto {

    protected String nimi;
    protected HashSet<Ote> otteet;

    public abstract String getNimi();

    public abstract void setNimi(String nimi);

    public abstract HashSet<Ote> getOtteet();

    public abstract void lisaaOte(Ote ote);

    @Override
    abstract public int hashCode();

    @Override
    abstract public boolean equals(Object toinen);
}
