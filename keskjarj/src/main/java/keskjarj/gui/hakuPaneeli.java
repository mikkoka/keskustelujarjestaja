/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.gui;

import java.awt.Dimension;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import keskjarj.keskjarj.*;

/**
 *
 * @author mikko
 */
public class hakuPaneeli extends JPanel {
    private Projekti projekti;
    
    public hakuPaneeli(Dimension koko, Projekti projekti)
    {
        this.projekti = projekti;
        JTable taulukko = new JTable(new MyTableModel());
        JScrollPane scrollPane = new JScrollPane(taulukko);
        taulukko.setPreferredScrollableViewportSize(koko);
        taulukko.setFillsViewportHeight(true);
        add(scrollPane);
    }

    
    private class MyTableModel extends AbstractTableModel {

    @Override
    public int getColumnCount() {
      return projekti.havainnot().size() + 1;//columnNames.length;
    }

    @Override
    public int getRowCount() {
      return projekti.kaikkiOtteet().size();
    }

    @Override
    public String getColumnName(int col) {
        if (col == 0)
            return "Ote";
        else
            return projekti.havainto(col - 1).getNimi();
    }

    @Override
    public Object getValueAt(int row, int col) {
        Ote o = projekti.ote(row);
        if (col == 0)
            return o.getTunnus();
        else {
            Havainto h = projekti.havainto(col - 1);
            return h.getOtteet().contains(o);
        }

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
        return false;
//      if (col < 1) {
//        return false;
//      } else {
//        return true;
//      }
   }

    /*
     * Don't need to implement this method unless your table's data can
     * change.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
      //data[row][col] = value;
      fireTableCellUpdated(row, col);
    }
  }
}
