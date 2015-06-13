/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.gui;

import java.awt.Dimension;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import keskjarj.keskjarj.*;

/**
 *
 * @author mikko
 */
public class HakuPaneeli extends JPanel implements TableModelListener {

    private Projekti projekti;
    JTable taulukko;
    TaulukkoMalli malli;

    public HakuPaneeli(Dimension koko, Projekti projekti) {
        malli = new TaulukkoMalli();
        this.projekti = projekti;
        taulukko = new JTable(malli);
        taulukko.getColumnModel().getColumn(0).setPreferredWidth(200);
        malli.addTableModelListener(this);
        taulukko.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(taulukko);
        taulukko.setPreferredScrollableViewportSize(koko);
        taulukko.setFillsViewportHeight(true);
//        TableColumn uusi = new TableColumn();
//        int modelIndex = uusi.getModelIndex();
//        uusi.setHeaderValue((Object)"nimi");
//        
//        taulukko.addColumn(uusi);

        add(scrollPane);
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        Havainto h = projekti.getHavainto(column - 1);
        Ote ote = projekti.getOte(row);
        if (h.getOtteet().contains(ote)) {
            h.poistaOte(ote);
        } else {
            h.lisaaOte(ote);
            projekti.tulostaOtteet();

        }
    }
        
    public Ote valittuOte() {
        int nro = taulukko.getSelectedRow();
        return malli.annaOte(nro);
    }


    private class TaulukkoMalli extends AbstractTableModel {

        @Override
        public int getColumnCount() {
            return projekti.getHavainnot().size() + 1;//columnNames.length;
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
