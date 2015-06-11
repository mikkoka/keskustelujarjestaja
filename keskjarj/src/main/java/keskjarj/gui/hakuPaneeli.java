/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.gui;

import java.awt.Dimension;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import keskjarj.keskjarj.Havainto;
import keskjarj.keskjarj.Projekti;

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
        private Object[] objektit = projekti.havainnot().toArray();
        private String[] columnNames;
        
        private MyTableModel()
        {
            
        Havainto[] havainnot = (Havainto[]) objektit;
        columnNames = new String[havainnot.length];
        for (int a  = 0; a < havainnot.length; a++)
            columnNames[a] = havainnot[a].getNimi();
            
    
//    { "First Name", "Last Name", "Sport",
//        "# of Years", "Vegetarian" };

    private Object[][] data = {
        { "Mary", "Campione", "Snowboarding", new Integer(5),
            new Boolean(false) },
        { "Alison", "Huml", "Rowing", new Integer(3), new Boolean(true) },
        { "Kathy", "Walrath", "Knitting", new Integer(2),
            new Boolean(false) },
        { "Sharon", "Zakhour", "Speed reading", new Integer(20),
            new Boolean(true) },
        { "Philip", "Milne", "Pool", new Integer(10),
            new Boolean(false) } };
    }

    @Override
    public int getColumnCount() {
      return columnNames.length;
    }

    @Override
    public int getRowCount() {
      return data.length;
    }

    @Override
    public String getColumnName(int col) {
      return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
      return data[row][col];
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
      if (col < 2) {
        return false;
      } else {
        return true;
      }
    }

    /*
     * Don't need to implement this method unless your table's data can
     * change.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
      data[row][col] = value;
      fireTableCellUpdated(row, col);
    }
  }
}
