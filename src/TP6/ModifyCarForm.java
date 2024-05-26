package TP6;

import javax.swing.*;	
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Map;
public class ModifyCarForm extends JPanel 
{
	JTextField modelField = new JTextField(60);
    JTextField brandField = new JTextField(60);
    JTextField colorField = new JTextField(60);
    JTextField yearField = new JTextField(60);
    JTextField priceField = new JTextField(60);
    JTextField numberField = new JTextField(60);
    JLabel photoField = new JLabel();
    JLabel m1 = new JLabel("Modele:");
    JLabel m2 = new JLabel("Marque:");
    JLabel m3 = new JLabel("Couleur:");
    JLabel m4 = new JLabel("Annee de production:");
    JLabel m5 = new JLabel("Prix:");
   // JLabel m6 = new JLabel("Nombre de voitures dans l'agence:");
    JLabel m7 = new JLabel("Image:");
    JButton submitButton = new JButton("Modifier");
    String matricule;
    public ModifyCarForm() 
    {
    	setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JLabel matriculeLabel = new JLabel("Matricule:");
        JTextField matriculeField = new JTextField(60);
        matriculeField.setMaximumSize(matriculeField.getPreferredSize());
        add(matriculeLabel);
        add(matriculeField);
        JButton searchButton = new JButton("Chercher");
        searchButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	matricule = matriculeField.getText();
            	if(!matricule.isEmpty())
            	{
            		Voiture v = Agence.findCarByMatricule(matricule);
                    if(v != null)
                    {
                    	Map<Client, Voiture> locat = Agence.readLocationsFromFile("locations");
                    	boolean isRented = false;
                    	for (Voiture voiture : locat.values()) 
                    	{
                    	    if (voiture.equalsByMatricule(v)) 
                    	    {
                    	        isRented = true;
                    	        break;
                    	    }
                    	}
                        if(isRented)
                            JOptionPane.showMessageDialog(null, "La voiture est actuellement louée et ne peut pas être modifiée", "Erreur", JOptionPane.ERROR_MESSAGE);
                        else 
                        {
                        	add(m1);
                            add(modelField);
                            modelField.setText(v.getModel());
                            modelField.setMaximumSize(modelField.getPreferredSize());
                            
                            add(m2);
                            add(brandField);
                            brandField.setText(v.getMarque());
                            brandField.setMaximumSize(brandField.getPreferredSize());
                            
                            add(m3);
                            add(colorField);
                            colorField.setText(v.getCouleur());
                            colorField.setMaximumSize(colorField.getPreferredSize());
                            
                            add(m4);
                            add(yearField);
                            yearField.setText(String.valueOf(v.getAnneeProduction()));
                            yearField.setMaximumSize(yearField.getPreferredSize());
                            
                            add(m5);
                            add(priceField);
                            priceField.setText(String.valueOf(v.getPrixLocation()));
                            priceField.setMaximumSize(priceField.getPreferredSize());
                            
                            /*add(m6);
                            add(numberField);
                            numberField.setText(String.valueOf(v.getNombre()));
                            numberField.setMaximumSize(numberField.getPreferredSize());*/
                            
                            add(m7);
                            add(photoField);
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
                                        photoField.setText(selectedFile.getAbsolutePath());
                                    }
                                }
                            });
                            add(chooseImageButton);
                            submitButton.addActionListener(new ActionListener() 
                            {
                                @Override
                                public void actionPerformed(ActionEvent e) 
                                {
                                	String model = modelField.getText();
                                    String brand = brandField.getText();
                                    String color = colorField.getText();
                                    int year = Integer.parseInt(yearField.getText());
                                    float price = Float.parseFloat(priceField.getText());
                                    //int number = Integer.parseInt(numberField.getText());
                                    String image = photoField.getText();
                                    Voiture voiture = new Voiture(matricule, brand, model, year, price, color, 1, image);
                                    Agence.supprimerVoiture(matricule);
                                    Agence.ajouterVoiture(voiture);
                                    Agence.writeVoituresToFile(Agence.getVoitures());
                                    Agence.updateLocations(matricule,voiture);
                                }
                            });
                            add(submitButton);
                            revalidate();
                            repaint();
                        }
                    }
                    else
                    	JOptionPane.showMessageDialog(null, "Voiture non trouvee", "Erreur", JOptionPane.ERROR_MESSAGE);
            	}
                else
                	JOptionPane.showMessageDialog(null, "Veuillez remplir le champ de matricule", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(searchButton);
    }
}