package TP6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AfficherClientForm extends JPanel 
{
    private JTable table;
    private JScrollPane scrollPane;

    public AfficherClientForm() 
    {
        setLayout(new BorderLayout());
        String[] columnNames = {"CNI", "Nom", "Prenom", "Civilite"};
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
        refreshButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                updateTable();
            }
        });
        JPanel p1 = new JPanel();
        p1.add(refreshButton);
        add(p1, BorderLayout.SOUTH);
    }

    private void updateTable() 
    {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0); 
        List<Client> clients = Agence.getClients();
        System.out.print(clients);
        for (Client client : clients) 
        {
            Object[] rowData = {client.getCin(), client.getNom(), client.getPrenom(), client.getCivilite()};
            tableModel.addRow(rowData);
        }
    }
    
}