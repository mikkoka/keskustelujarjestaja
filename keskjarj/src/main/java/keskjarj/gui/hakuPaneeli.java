/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keskjarj.gui;

import java.awt.Dimension;
import javax.swing.*;

/**
 *
 * @author mikko
 */
public class hakuPaneeli extends JPanel {
    
    public hakuPaneeli(Dimension koko)
    {
            String[] columnNames = {"First Name",
                                "Last Name",
                                "Sport",
                                "# of Years",
                                "Vegetarian"};
 
        Object[][] data = {
        {"Kathy", "Smith", "Snowboarding", 5, false},
        {"John", "Doe", "Rowing", 3, true},
        {"Sue", "Black","Knitting", 2, false},
        {"Jane", "White", "Speed reading", 20, true},
        {"Joe", "Brown", "Pool", 10, false}};
 
        JTable taulukko = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(taulukko);
        taulukko.setPreferredScrollableViewportSize(koko);
        taulukko.setFillsViewportHeight(true);
        add(scrollPane);
        
       
    }
    
    
    

        

    
}
