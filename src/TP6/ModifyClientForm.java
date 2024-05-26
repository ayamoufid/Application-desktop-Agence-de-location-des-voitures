package TP6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ModifyClientForm extends JPanel 
{
    JTextField cniField = new JTextField(60);
    JTextField nomField = new JTextField(60);
    JTextField prenomField = new JTextField(60);
    JRadioButton mrRadio = new JRadioButton("Monsieur");
    JRadioButton mmeRadio = new JRadioButton("Madame");
    JRadioButton mlleRadio = new JRadioButton("Mademoiselle");
    JButton submitButton = new JButton("Modifier");
    String cni;

    public ModifyClientForm() 
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JLabel cniLabel = new JLabel("CIN:");
        cniField.setMaximumSize(cniField.getPreferredSize());
        add(cniLabel);
        add(cniField);

        JButton searchButton = new JButton("Chercher");
        searchButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                cni = cniField.getText();
                Client client = Agence.findClientByCNI(cni);                
                if (client != null) 
                {
                    add(new JLabel("Nom:"));
                    add(nomField);
                    nomField.setText(client.getNom());
                    nomField.setMaximumSize(nomField.getPreferredSize());

                    add(new JLabel("Prenom:"));
                    add(prenomField);
                    prenomField.setText(client.getPrenom());
                    prenomField.setMaximumSize(prenomField.getPreferredSize());

                    add(new JLabel("Genre:"));
                    ButtonGroup genreGroup = new ButtonGroup();
                    JPanel genrePanel = new JPanel();
                    genrePanel.setLayout(new BoxLayout(genrePanel, BoxLayout.LINE_AXIS));
                    genreGroup.add(mrRadio);
                    genreGroup.add(mmeRadio);
                    genreGroup.add(mlleRadio);
                    genrePanel.add(mrRadio);
                    genrePanel.add(mmeRadio);
                    genrePanel.add(mlleRadio);
                    add(genrePanel);
                    setGenre(client.getCivilite());

                    submitButton.addActionListener(new ActionListener() 
                    {
                        @Override
                        public void actionPerformed(ActionEvent e) 
                        {
                            String nom = nomField.getText();
                            String prenom = prenomField.getText();
                            String genre = getSelectedGenre();
                            Client updatedClient = new Client (nom, prenom,cni, genre);                        
                            Agence.getClients().add(updatedClient);
                            Agence.deleteClientByCNI("clients",cni);
                            Agence.addClientToFile("clients", updatedClient);
                            Agence.setClients(Agence.readClientsFromFile("clients"));                         
                            Client cl = Agence.findClientByCin(cni);
                            if (cl!=null) 
                            {
                            	Client cl2 = new Client(nom, prenom,cni, genre,cl.getDateLocation(),cl.getHeureLocation());
                            	Agence.updateLocations1(cni,cl2);
                            }
                            JOptionPane.showMessageDialog(null, "Client modifier avec succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
                    add(submitButton);
                    revalidate();
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Client non trouve", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(searchButton);
    }

    private String getSelectedGenre() 
    {
        if (mrRadio.isSelected()) 
            return "Monsieur";
        else if (mmeRadio.isSelected()) 
                return "Madame";
             else if (mlleRadio.isSelected()) 
                     return "Mademoiselle";
        return "";
    }

    private void setGenre(String genre) 
    {
        switch (genre) 
        {
            case "Mr" : mrRadio.setSelected(true);
            		   break;
            case "Mme" : mmeRadio.setSelected(true);
            			break;               
            case "Mlle" : mlleRadio.setSelected(true);
            			  break;          
            default : break;
        }
    }
}
