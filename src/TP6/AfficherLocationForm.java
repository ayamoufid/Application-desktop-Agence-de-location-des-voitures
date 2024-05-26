package TP6;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import java.text.SimpleDateFormat;


public class AfficherLocationForm extends JPanel 
{
    private JTable table;
    private JScrollPane scrollPane;

    public AfficherLocationForm() 
    {
        setLayout(new BorderLayout());
        String[] columnNames = {"Client CNI", "Voiture Matricule", "Date de Location", "Heure de Location"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) 
        {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        updateTable();
        JButton refreshButton = new JButton("Actualiser");
        refreshButton.addActionListener(e -> updateTable());
        JPanel p1 = new JPanel();
        p1.add(refreshButton);
        add(p1, BorderLayout.SOUTH);
        
    }

    private void updateTable() 
    {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0); 
        Map<Client, Voiture> locations = Agence.getLocations();
        for (Map.Entry<Client, Voiture> entry : locations.entrySet()) 
        {
            Client client = entry.getKey();
            Voiture voiture = entry.getValue();
            String chaine =client.getHeureLocation();
            String[] parties = chaine.split("\\.");
            String partieAvantPoint = parties[0];
            Object[] rowData = {client.getCin(), voiture.getMatricule(), client.getDateLocation(), partieAvantPoint};
            tableModel.addRow(rowData);
        }

        for (int i = 0; i < table.getColumnCount(); i++) 
            table.getColumnModel().getColumn(i).setPreferredWidth(150); 
    }
}