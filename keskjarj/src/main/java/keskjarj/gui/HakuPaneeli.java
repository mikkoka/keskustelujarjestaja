/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import keskjarj.keskjarj.*;

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
        taulukko = new JTable(malli);
        taulukko.getColumnModel().getColumn(0).setPreferredWidth(200);
        malli.addTableModelListener(this);
        taulukko.setAutoCreateRowSorter(true);
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
            Ote ote = projekti.getOte(row);
            if (h.getOtteet().contains(ote)) {
                h.poistaOte(ote);
            } else {
                h.lisaaOte(ote);
            }
            projekti.tulostaHavainnot();
        }
    }
        
    public Ote valittuOte() {
        int[] temp = taulukko.getSelectedRows();
        if (temp == null || temp.length != 1)
            return null;
        return malli.annaOte(temp[0]);
    }
    
        public Ote[] valitutOtteet() {
        int[] temp = taulukko.getSelectedRows();
        return projekti.getOtteet(temp); 
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
            Ote o = projekti.getOte(row);
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
            return projekti.getOte(row);
        }
        
        public Ote[] annaOtteet(int[] rows) {
            return projekti.getOtteet(rows);
        }


        /*
         * JTable uses this method to determine the default renderer/ editor for
         * each cell. If we didn't implement this method, then the last column
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