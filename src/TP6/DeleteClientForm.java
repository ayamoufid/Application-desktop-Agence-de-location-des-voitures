package TP6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class DeleteClientForm extends JPanel 
{
    public DeleteClientForm() 
    {
        JLabel cniLabel = new JLabel("CNI:");
        JTextField cniField = new JTextField(40);
        add(cniLabel);
        add(cniField);

        JButton submitButton = new JButton("Supprimer");
        submitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String cni = cniField.getText();
                Client cl = Agence.findClientByCin(cni);
                if (cl!=null) 
                    JOptionPane.showMessageDialog(null, "Le client a actuellement une voiture louee et ne peut pas etre supprime", "Erreur", JOptionPane.ERROR_MESSAGE);
                else if(!Agence.doesClientExist(cni)) 
                	 	JOptionPane.showMessageDialog(null, "Le client n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);
                     else
                     {
                    	 int dialogResult = JOptionPane.showConfirmDialog(null, "Etes-vous sur de vouloir supprimer ce client?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    	 if (dialogResult == JOptionPane.YES_OPTION) 
                    	 {
                    		 Agence.deleteClientByCNI("clients",cni);
                             cniField.setText("");
                             Agence.setClients(Agence.readClientsFromFile("clients"));
                             JOptionPane.showMessageDialog(null, "Client supprime avec succes", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                     }
            }
        });
        add(submitButton);
    }
}
