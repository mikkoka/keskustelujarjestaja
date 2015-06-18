/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.gui;

import keskjarj.tieto.Havainto;
import keskjarj.tieto.Projekti;
import keskjarj.tieto.Ote;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 *
 * @author mikko
 */
public class HakuPaneeli extends JPanel implements TableModelListener {

    private Projekti projekti;
    JTable taulukko;
    TaulukkoMalli malli;

    /**
     * 
     * @param koko
     * @param projekti
     */
    public HakuPaneeli(Dimension koko, Projekti projekti) {
        malli = new TaulukkoMalli();
        this.projekti = projekti;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        taulukko = new JTable(malli);
        taulukko.getColumnModel().getColumn(0).setPreferredWidth(200);
        malli.addTableModelListener(this);
        JScrollPane vierityspaneeli = new JScrollPane(taulukko, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        taulukko.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        taulukko.setPreferredScrollableViewportSize(koko);
        taulukko.setFillsViewportHeight(true); 
        add(vierityspaneeli);
    }
    
    public void paivitaSarakkeet (String otsikko) {
        TableColumn temp = new TableColumn();
        temp.setHeaderValue((Object)otsikko);
        malli.fireTableStructureChanged();
        taulukko.getColumnModel().getColumn(0).setPreferredWidth(200);
    }
    
    public void hakuMahdollista (boolean mahdollista) {
        taulukko.setAutoCreateRowSorter(mahdollista);
    }
    
    public boolean hakuMahdollista()
    {
        return taulukko.getAutoCreateRowSorter();
    }
    
        public void paivitaTaulukko() {
        malli.fireTableStructureChanged();
        taulukko.getColumnModel().getColumn(0).setPreferredWidth(200);
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        
        if (row > -1 || column > -1) { //-1 tarkoittaa, ettei taulukkoa ole muutettu (data voi silti olla muuttunut)
            Havainto h = projekti.getHavainto(column - 1);
            Ote ote = projekti.getProjektinOte(row);
            if (h.getOtteet().contains(ote)) {
                h.poistaOte(ote);
            } else {
                h.lisaaOte(ote);
            }
        }
    }
        
    public Ote valittuOte() {
        int[] temp = taulukko.getSelectedRows();
        if (temp == null || temp.length != 1)
            return null;
        return malli.annaOte(temp[0]);
    }
    
        public int[] valitutOtteet() {
            return taulukko.getSelectedRows();

    }

    private class TaulukkoMalli extends AbstractTableModel {

        @Override
        public int getColumnCount() {
            return projekti.getHavainnot().size() + 1;
        }

        @Override
        public int getRowCount() {
            return projekti.getOtteet().size();
        }

        @Override
        public String getColumnName(int col) {
            if (col == 0) {
                return "Ote";
            } else {
                return projekti.getHavainto(col - 1).getNimi();
            }
        }

        @Override
        public Object getValueAt(int row, int col) {
            Ote o = projekti.getProjektinOte(row);
            if (o == null)
                return null;  
            if (col == 0) {
                return o.getTunnus();
            } else {
                Havainto h = projekti.getHavainto(col - 1);
                return h.getOtteet().contains(o);
            }
        }

        public Havainto annaHavainto(int col) {
            return projekti.getHavainto(col - 1);
        }

        public Ote annaOte(int row) {
            return projekti.getProjektinOte(row);
        }
        
        public Ote[] annaOtteet(int[] rows) {
            return projekti.getOtteet(rows);
        }


        /*
         * JTable uses this method to determine the default renderer/ editor for
         * each cell. If we didn't implement this method, then the boolean columns
         * would contain text ("true"/"false"), rather than a check box.
         */
        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's editable.
         */
        @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            return col >= 1;
        }

        /*
         * Don't need to implement this method unless your table's data can
         * change.
         */
        @Override
        public void setValueAt(Object value, int row, int col) {
            fireTableCellUpdated(row, col);
        }
    }
}
