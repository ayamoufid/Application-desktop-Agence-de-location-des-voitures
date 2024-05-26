package TP6;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AfficherVoitureForm extends JPanel 
{
    private JTable table;
    private JScrollPane scrollPane;

    public AfficherVoitureForm() 
    {
        setLayout(new BorderLayout());
        String[] columnNames = {"matricule", "marque", "model", "anneeProduction", "prixLocation", "couleur", "nombreDisponible"};

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
        JPanel pp = new JPanel();
        refreshButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                updateTable();
            }
        });
        pp.add(refreshButton);
        add(pp, BorderLayout.SOUTH);
        updateTable();
    }

    private void updateTable() 
    {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0); 
        List<Voiture> voitures = Agence.getVoitures();

        for (Voiture v : voitures) 
        {
            Object[] rowData = {v.getMatricule(), v.getMarque(), v.getModel(), v.getAnneeProduction(), v.getPrixLocation(), v.getCouleur(), v.getNombreDisponible()};
            tableModel.addRow(rowData);
        }

        for (int i = 0; i < table.getColumnCount(); i++) 
            table.getColumnModel().getColumn(i).setPreferredWidth(150); 
    }

    
}




