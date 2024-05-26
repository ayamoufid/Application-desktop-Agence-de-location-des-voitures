package TP6;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class AddCarForm extends JPanel 
{
    private JTextField matriculeField;
    private JTextField modelField;
    private JTextField brandField;
    private JTextField colorField;
    private JTextField yearField;
    private JTextField priceField;
    private JTextField qtField;
    private JLabel imageLabel;

    public AddCarForm() 
    {
        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel matriculeLabel = new JLabel("Matricule:");
        matriculeField = new JTextField(20);
        formPanel.add(matriculeLabel, gbc);
        gbc.gridx++;
        formPanel.add(matriculeField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel modelLabel = new JLabel("Modele:");
        modelField = new JTextField(20);
        formPanel.add(modelLabel, gbc);
        gbc.gridx++;
        formPanel.add(modelField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel brandLabel = new JLabel("Marque:");
        brandField = new JTextField(20);
        formPanel.add(brandLabel, gbc);
        gbc.gridx++;
        formPanel.add(brandField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel colorLabel = new JLabel("Couleur:");
        colorField = new JTextField(20);
        formPanel.add(colorLabel, gbc);
        gbc.gridx++;
        formPanel.add(colorField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel yearLabel = new JLabel("Annee de production:");
        yearField = new JTextField(20);
        formPanel.add(yearLabel, gbc);
        gbc.gridx++;
        formPanel.add(yearField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel priceLabel = new JLabel("Prix:");
        priceField = new JTextField(20);
        formPanel.add(priceLabel, gbc);
        gbc.gridx++;
        formPanel.add(priceField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        //JLabel qtLabel = new JLabel("Quantite:");
        //qtField = new JTextField(20);
        //formPanel.add(qtLabel, gbc);
        //gbc.gridx++;
        //formPanel.add(qtField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel imageTextLabel = new JLabel("Image:");
        formPanel.add(imageTextLabel, gbc);
        gbc.gridx++;
        imageLabel = new JLabel();
        formPanel.add(imageLabel, gbc);
        gbc.gridx++;
        JButton chooseImageButton = new JButton("Choisir Image");
        chooseImageButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choisir une image");
                int userSelection = fileChooser.showOpenDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) 
                {
                    File selectedFile = fileChooser.getSelectedFile();
                    imageLabel.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        formPanel.add(chooseImageButton, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JButton submitButton = new JButton("Ajouter");
        submitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (areAllFieldsFilled()) 
                {
                    String matricule = matriculeField.getText();
                    String model = modelField.getText();
                    String brand = brandField.getText();
                    String color = colorField.getText();
                    String year = yearField.getText();
                    String price = priceField.getText();
                   // String qt = qtField.getText();
                    String imagePath = imageLabel.getText(); 
                    Voiture voiture = new Voiture(matricule, brand, model, Integer.parseInt(year), Float.parseFloat(price), color, 1,imagePath);
                    Agence.ajouterVoiture(voiture);
                    Agence.writeVoituresToFile(Agence.getVoitures());
                    clearFields();
                } 
                else 
                    JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les cases.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(formPanel, BorderLayout.CENTER);
        JPanel p1 = new JPanel();
        p1.add(submitButton);
        add(p1, BorderLayout.SOUTH);
    }

    private boolean areAllFieldsFilled() 
    {
        return !matriculeField.getText().isEmpty() &&
               !modelField.getText().isEmpty() &&
               !brandField.getText().isEmpty() &&
               !colorField.getText().isEmpty() &&
               !yearField.getText().isEmpty() &&
               !priceField.getText().isEmpty() &&
               !qtField.getText().isEmpty() &&
               !imageLabel.getText().isEmpty();
    }

    private void clearFields() 
    {
        matriculeField.setText("");
        modelField.setText("");
        brandField.setText("");
        colorField.setText("");
        yearField.setText("");
        priceField.setText("");
        qtField.setText("");
        imageLabel.setText("");
    }
    
    
    
    
}