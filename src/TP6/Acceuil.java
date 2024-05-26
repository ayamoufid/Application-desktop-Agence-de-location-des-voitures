package TP6;

import javax.swing.*;	
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
class CarDetailsPage extends JPanel 
{
    public CarDetailsPage(Voiture voiture) 
    {
        setLayout(new BorderLayout());

        JLabel marqueLabel = new JLabel("Marque: " + voiture.getMarque());
        JLabel modeleLabel = new JLabel("Modele: " + voiture.getModel());
        JLabel anneeLabel = new JLabel("Annee de production: " + voiture.getAnneeProduction());
        JLabel couleurLabel = new JLabel("Couleur: " + voiture.getCouleur());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.add(marqueLabel);
        detailsPanel.add(modeleLabel);
        detailsPanel.add(anneeLabel);
        detailsPanel.add(couleurLabel);

        add(detailsPanel, BorderLayout.CENTER);
    }
}

public class Acceuil extends JPanel 
{
	JPanel criteriaAndButtonPanel;
	private JPanel carPanel;
    public Acceuil(JPanel cards)
    {
        criteriaAndButtonPanel = new JPanel();
        criteriaAndButtonPanel.setLayout(new BoxLayout(criteriaAndButtonPanel, BoxLayout.Y_AXIS));
 
        carPanel = new JPanel();
        carPanel.setLayout(new GridLayout(0, 3));
        for (Voiture voiture : Agence.getVoitures()) 
        {
            JPanel carContainerPanel = new JPanel(new BorderLayout());
            carContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            ImageIcon originalCar = new ImageIcon(voiture.getImage());
            Image carImage = originalCar.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            JLabel carLabel = new JLabel(new ImageIcon(carImage));
            carContainerPanel.add(carLabel, BorderLayout.CENTER);
            JLabel detailsLabel = new JLabel("<html>Marque: " + voiture.getMarque() + "<br>Modele: " + voiture.getModel()+ "<br>Prix: " + voiture.getPrixLocation() + "</html>");
            carContainerPanel.add(detailsLabel, BorderLayout.NORTH);
            JButton carButton = new JButton("Voir plus");
            carButton.setPreferredSize(new Dimension(100, 30)); 
            carButton.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    Voiture v = voiture; 
                    showCarDetails(v);
                }
            });
            carContainerPanel.add(carButton, BorderLayout.SOUTH);
            carPanel.add(carContainerPanel);
        }
        JButton actualiserbutton = new JButton("Actualiser page");
        actualiserbutton.addActionListener(e -> refreshCars());
        JPanel newPanel = new JPanel(new BorderLayout());
        newPanel.add(criteriaAndButtonPanel, BorderLayout.CENTER);
        newPanel.add(carPanel, BorderLayout.SOUTH);
        this.add(newPanel, BorderLayout.CENTER);
        JPanel pp1 = new JPanel();
        pp1.add(actualiserbutton);
        add(pp1,BorderLayout.SOUTH);
    }

    public void showCarDetails(Voiture v) 
    {
    	JDialog detailsDialog = new JDialog();
    	detailsDialog.setTitle("Détails de la voiture");
    	detailsDialog.setSize(600, 400);

    	JPanel detailsPanel = new JPanel();
    	detailsPanel.setLayout(new BorderLayout());

    	JPanel infoPanel = new JPanel(new GridLayout(9, 2));

    	infoPanel.add(new JLabel("Matricule:"));
    	infoPanel.add(new JLabel(v.getMatricule()));
    	infoPanel.add(new JLabel("Marque:"));
    	infoPanel.add(new JLabel(v.getMarque()));
    	infoPanel.add(new JLabel("Modèle:"));
    	infoPanel.add(new JLabel(v.getModel()));
    	infoPanel.add(new JLabel("Année de production:"));
    	infoPanel.add(new JLabel(String.valueOf(v.getAnneeProduction())));
    	infoPanel.add(new JLabel("Prix de location:"));
    	infoPanel.add(new JLabel(String.valueOf(v.getPrixLocation())));
    	infoPanel.add(new JLabel("Couleur:"));
    	infoPanel.add(new JLabel(v.getCouleur()));
    	//infoPanel.add(new JLabel("Nombre total:"));
    	//infoPanel.add(new JLabel(String.valueOf(v.getNombre())));
    	//infoPanel.add(new JLabel("Nombre disponible:"));
    	//infoPanel.add(new JLabel(String.valueOf(v.getNombreDisponible())));
    	detailsPanel.add(infoPanel, BorderLayout.NORTH);

    	ImageIcon carImageIcon = new ImageIcon(v.getImage());
    	Image scaledImage = carImageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_DEFAULT); // Adjust the image size
    	JLabel carImageLabel = new JLabel(new ImageIcon(scaledImage));
    	detailsPanel.add(carImageLabel, BorderLayout.CENTER);

        if(v.getNombreDisponible()>0)
        {
        	JButton louerButton = new JButton("Louer");
       	 	JPanel buttonPanel = new JPanel(); 
            buttonPanel.add(louerButton);
	        louerButton.addActionListener(new ActionListener() 
	        {
	            @Override
	            public void actionPerformed(ActionEvent e) 
	            {
	            	JDialog clientInfoDialog = new JDialog();
	            	clientInfoDialog.setTitle("Information du Client");
	            	clientInfoDialog.setSize(350, 300);

	            	JPanel clientInfoPanel = new JPanel();
	            	clientInfoPanel.setLayout(new GridLayout(6,2));
	            	JTextField cinField = new JTextField();

	            	clientInfoPanel.add(new JLabel("CIN:"));
	            	clientInfoPanel.add(cinField);	            	
	            	
	            	JButton validerButton = new JButton("Louer");
	            	validerButton.addActionListener(new ActionListener() 
	            	{
	                    @Override
	                    public void actionPerformed(ActionEvent e) 
	                    {
	                    	String cin = cinField.getText();
	                    	if(Agence.doesClientExist(cin))
	                    	{
	                    		Client client = Agence.findClientByCNI(cin);
		                        try 
		                        {
		                        	if (Agence.loueVoiture(client, v)) 
		                        	{
		                        		Object[] options = {"OK"};
		                        		int n = JOptionPane.showOptionDialog(null,"Voiture louée avec succès pour " + client.getCivilite() + ". " + client.getPrenom() + " " + client.getNom() + " !","Résultat de la location",JOptionPane.PLAIN_MESSAGE,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
		                        		if (n == 0) 
		                        		{
		                        			clientInfoDialog.dispose();
		                        			detailsDialog.dispose(); 
		                        		}
		                        	}                   
		                            else 
		                                JOptionPane.showMessageDialog(null,"vous avez deja loue une voiture","",JOptionPane.ERROR_MESSAGE);
		                        } 
		                        catch (Exception e1) 
		                        {
		                            e1.printStackTrace();
		                        }
	                    	}
	                    	else
	                    	{
	                    		clientInfoDialog.dispose();
	                    		JDialog Dialog1 = new JDialog();
	                    		Dialog1.setTitle("Information du Client");
	                    		Dialog1.setSize(350, 300);

	        	            	JPanel panel1 = new JPanel();
	        	            	panel1.setLayout(new GridLayout(4,2));
	        	            	
	        	            	JTextField nomField = new JTextField();
	        	            	JTextField prenomField = new JTextField();
	        	            	
	        	            	panel1.add(new JLabel("Nom:"));
	        	            	panel1.add(nomField);
	        	            	panel1.add(new JLabel("Prénom:"));
	        	            	panel1.add(prenomField);            	
	        	            	panel1.add(new JLabel("Civilité:"));
	        	            	ButtonGroup civiliteGroup = new ButtonGroup();
	        	            	JRadioButton mmeButton = new JRadioButton("Mlle");
	        	            	JRadioButton mdmButton = new JRadioButton("Mme");
	        	            	JRadioButton mButton = new JRadioButton("Mr");
	        	            	civiliteGroup.add(mmeButton);
	        	            	civiliteGroup.add(mdmButton);
	        	            	civiliteGroup.add(mButton);
	        	            	JPanel civilitePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        	            	civilitePanel1.add(new JPanel().add(mmeButton));
	        	            	civilitePanel1.add(new JPanel().add(mdmButton));
	        	            	civilitePanel1.add(new JPanel().add(mButton));
	        	            	panel1.add(civilitePanel1);
	                    		JButton louerbt = new JButton("Louer");
	                    		louerbt.addActionListener(new ActionListener()
	                    				{
	                    					public void actionPerformed(ActionEvent e)
	                    					{
	                    						String nom = nomField.getText();
	            		                        String prenom = prenomField.getText();
	            		                        String civilite = "";
	            		                        if (mmeButton.isSelected()) 
	            		                            civilite = "Mlle";
	            		                        else if (mdmButton.isSelected()) 
	            		                            	civilite = "Mme";
	            		                             else if (mButton.isSelected()) 
	            		                            	 civilite = "Mr";                       
	            		                        if (nom.isEmpty() || prenom.isEmpty() || cin.isEmpty() || (!mmeButton.isSelected() && !mdmButton.isSelected() && !mButton.isSelected())) 
	            		                        {
	            		                            JOptionPane.showMessageDialog(null,"Veuillez remplir tous les champs et sélectionner une civilité.","Erreur de saisie",JOptionPane.ERROR_MESSAGE);
	            		                            return;
	            		                        }
	            		                        Client client = new Client(nom,prenom,cin,civilite);
	            		                        try 
	            		                        {
	            		                        	if (Agence.loueVoiture(client, v)) 
	            		                        	{
	            		                        		Object[] options = {"OK"};
	            		                        		int n = JOptionPane.showOptionDialog(null,"Voiture louée avec succès!","Résultat de la location",JOptionPane.PLAIN_MESSAGE,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
	            		                        		if (n == 0) 
	            		                        		{
	            		                        			Dialog1.dispose();
	            		                        			detailsDialog.dispose(); 
	            		                        		}
	            		                        	}
	            		                            else 
	            		                                JOptionPane.showMessageDialog(null,"vous avez deja loue une voiture","",JOptionPane.ERROR_MESSAGE);
	            		                        } 
	            		                        catch (Exception e1) 
	            		                        {
	            		                            e1.printStackTrace();
	            		                        }
	                    					}
	                    					
	                    				});
	                    		panel1.add(new JPanel());
	                    		panel1.add(louerbt);
	                    		Dialog1.add(panel1);
	                    		Dialog1.setVisible(true);
	                    	}	
                        }
                   });
                clientInfoPanel.add(new JPanel());
                clientInfoPanel.add(validerButton);
                clientInfoDialog.add(clientInfoPanel);
                clientInfoDialog.setVisible(true);
            }
        });      
        detailsPanel.add(buttonPanel, BorderLayout.SOUTH);
        }
        detailsDialog.add(detailsPanel);
        detailsDialog.setVisible(true);
    }
    
    
   
    public void refreshCars() 
    {
    	carPanel.removeAll();
    	for (Voiture voiture : Agence.getVoitures()) 
        {
            JPanel carContainerPanel = new JPanel(new BorderLayout());
            carContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            ImageIcon originalCar = new ImageIcon(voiture.getImage());
            Image carImage = originalCar.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            JLabel carLabel = new JLabel(new ImageIcon(carImage));
            carContainerPanel.add(carLabel, BorderLayout.CENTER);
            JLabel detailsLabel = new JLabel("<html>Marque: " + voiture.getMarque() + "<br>Modele: " + voiture.getModel()+ "<br>Prix: " + voiture.getPrixLocation() + "</html>");
            carContainerPanel.add(detailsLabel, BorderLayout.NORTH);
            JButton carButton = new JButton("Voir plus");
            carButton.setPreferredSize(new Dimension(100, 30)); 
            carButton.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    Voiture v = voiture; 
                    showCarDetails(v);
                }
            });
            carContainerPanel.add(carButton, BorderLayout.SOUTH);
            carPanel.add(carContainerPanel);
        } 
        carPanel.revalidate();
        carPanel.repaint();
    }
    
    
    
}