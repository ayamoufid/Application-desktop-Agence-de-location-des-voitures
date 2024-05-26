package TP6;

import javax.swing.*;	
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
public class DeleteCarForm extends JPanel 
{
    public DeleteCarForm() 
    {
        //setLayout(new GridLayout(2, 2));

        JLabel matriculeLabel = new JLabel("Matricule:");
        JTextField matriculeField = new JTextField(40);
        add(matriculeLabel);
        add(matriculeField);

        JButton submitButton = new JButton("Supprimer");
        submitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String matricule = matriculeField.getText();
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
                            JOptionPane.showMessageDialog(null, "La voiture est actuellement louée et ne peut pas être supprimée", "Erreur", JOptionPane.ERROR_MESSAGE);
                        else 
                        {
                            int dialogResult = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer cette voiture?", "Confirmation", JOptionPane.YES_NO_OPTION);
                            if(dialogResult == JOptionPane.YES_OPTION)
                            {
                                Agence.supprimerVoiture(matricule);
                                Agence.writeVoituresToFile(Agence.getVoitures());                         
                                matriculeField.setText("");
                                JOptionPane.showMessageDialog(null, "Voiture supprimée avec succès", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } 
                    else 
                        JOptionPane.showMessageDialog(null, "Voiture non trouvée", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                else 
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un matricule", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(submitButton);
    }
}