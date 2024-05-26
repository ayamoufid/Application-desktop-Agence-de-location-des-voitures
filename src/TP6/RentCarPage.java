/*package TP6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


@SuppressWarnings("serial")
public class RentCarPage extends JPanel 
{
    private InterCritere crit = new InterCritere();
    public String prix;
    public String marque;
    public String modele;
    public String annee;
    public String couleur;
    //private JPanel cards;  
    private JPanel carPanel; 

    public RentCarPage(JPanel cards) 
    {
        //this.cards = cards;  
        JPanel header = new JPanel();
        JPanel criteriaAndButtonPanel = new JPanel();
        criteriaAndButtonPanel.setLayout(new BoxLayout(criteriaAndButtonPanel, BoxLayout.Y_AXIS));

        JPanel criteriaPanel = new JPanel();
        criteriaPanel.add(new JLabel("Prix:"));
        criteriaPanel.add(new JTextField(10));
        criteriaPanel.add(new JLabel("Marque:"));
        criteriaPanel.add(new JComboBox<>(Agence.getDistinctMarquesFromLocations()));
        criteriaPanel.add(new JLabel("Modele:"));
        criteriaPanel.add(new JComboBox<>(Agence.getDistinctModelesFromLocations()));
        criteriaPanel.add(new JLabel("Annee:"));
        criteriaPanel.add(new JTextField(4));
        criteriaPanel.add(new JLabel("Couleur:"));
        criteriaPanel.add(new JComboBox<>(Agence.getDistinctCouleursFromLocations()));

        JButton applyButton = new JButton("Appliquer");
        applyButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                prix = ((JTextField) criteriaPanel.getComponent(1)).getText();
                String marque = (String) ((JComboBox<?>) criteriaPanel.getComponent(3)).getSelectedItem();
                String modele = (String) ((JComboBox<?>) criteriaPanel.getComponent(5)).getSelectedItem();
                String annee = ((JTextField) criteriaPanel.getComponent(7)).getText();
                String couleur = (String) ((JComboBox<?>) criteriaPanel.getComponent(9)).getSelectedItem();

                if (!prix.isEmpty())
                	crit.addCritere(new CriterePrix(Float.parseFloat(prix)));
                if (!"Tous".equals(marque))
                	crit.addCritere(new CritereMarque(marque));
            	if (!"Tous".equals(modele))
            	crit.addCritere(new CritereModele(modele));
                if (!annee.isEmpty())
                	crit.addCritere(new CritereAnneeProd(Integer.parseInt(annee)));
                if (!"Tous".equals(couleur))
                	crit.addCritere(new CritereCouleur(couleur));
                displayFilteredCars();
                crit.viderCritere();
            }
        });
        criteriaAndButtonPanel.add(criteriaPanel);
        criteriaAndButtonPanel.add(applyButton);

        carPanel = new JPanel(); 

        carPanel.setLayout(new GridLayout(0, 3));
        displayFilteredCars();  

        JPanel newPanel = new JPanel(new BorderLayout());
        newPanel.add(header, BorderLayout.NORTH);
        newPanel.add(criteriaAndButtonPanel, BorderLayout.CENTER);
        newPanel.add(carPanel, BorderLayout.SOUTH);

        this.add(newPanel, BorderLayout.CENTER);
    }

    private void displayFilteredCars() 
    {
        List<Voiture> filteredCars = Agence.afficheSelection(crit);
        carPanel.removeAll();  
        if(filteredCars == null)
        {
        	JOptionPane.showMessageDialog(this,"Aucune voiture n'est disponible avec ces criteres. Veuillez selectionner des criteres valides.","",JOptionPane.ERROR_MESSAGE);
        	for (Voiture voiture : Agence.getVoitures()) 
            {
        		if(voiture.getNombreDisponible()>0)
            	{
                JPanel carContainerPanel = new JPanel(new BorderLayout());

                carContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                ImageIcon originalCar = new ImageIcon(voiture.getImage());
                Image carImage = originalCar.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
                JLabel carLabel = new JLabel(new ImageIcon(carImage));
                carContainerPanel.add(carLabel, BorderLayout.CENTER);

                JLabel detailsLabel = new JLabel("<html>Marque: " + voiture.getMarque() + "<br>Mod�le: " + voiture.getModel()+ "<br>Annee: " + voiture.getAnneeProduction() + "</html>");
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
            }
        }
        else
        {
        	for (Voiture voiture : filteredCars) 
            {
            	if(voiture.getNombreDisponible()>0)
            	{
            		JPanel carContainerPanel = new JPanel(new BorderLayout());

                    carContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    ImageIcon originalCar = new ImageIcon(voiture.getImage());
                    Image carImage = originalCar.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
                    JLabel carLabel = new JLabel(new ImageIcon(carImage));
                    carContainerPanel.add(carLabel, BorderLayout.CENTER);

                    JLabel detailsLabel = new JLabel("<html>Marque: " + voiture.getMarque() + "<br>Mod�le: " + voiture.getModel()+ "<br>Annee: " + voiture.getAnneeProduction() + "</html>");
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
            }
        }

        carPanel.revalidate();
        carPanel.repaint();
    }
    
    public void showCarDetails(Voiture v) 
    {
    	JDialog detailsDialog = new JDialog();
    	detailsDialog.setTitle("D�tails de la voiture");
    	detailsDialog.setSize(600, 400);

    	JPanel detailsPanel = new JPanel();
    	detailsPanel.setLayout(new BorderLayout());

    	JPanel infoPanel = new JPanel(new GridLayout(9, 2));

    	infoPanel.add(new JLabel("Matricule:"));
    	infoPanel.add(new JLabel(v.getMatricule()));
    	infoPanel.add(new JLabel("Marque:"));
    	infoPanel.add(new JLabel(v.getMarque()));
    	infoPanel.add(new JLabel("Mod�le:"));
    	infoPanel.add(new JLabel(v.getModel()));
    	infoPanel.add(new JLabel("Ann�e de production:"));
    	infoPanel.add(new JLabel(String.valueOf(v.getAnneeProduction())));
    	infoPanel.add(new JLabel("Prix de location:"));
    	infoPanel.add(new JLabel(String.valueOf(v.getPrixLocation())));
    	infoPanel.add(new JLabel("Couleur:"));
    	infoPanel.add(new JLabel(v.getCouleur()));
    	infoPanel.add(new JLabel("Nombre total:"));
    	infoPanel.add(new JLabel(String.valueOf(v.getNombre())));
    	infoPanel.add(new JLabel("Nombre disponible:"));
    	infoPanel.add(new JLabel(String.valueOf(v.getNombreDisponible())));
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

	            	JTextField nomField = new JTextField();
	            	JTextField prenomField = new JTextField();
	            	JTextField cinField = new JTextField();

	            	clientInfoPanel.add(new JLabel("Civilit�:"));

	            	ButtonGroup civiliteGroup = new ButtonGroup();
	            	JRadioButton mmeButton = new JRadioButton("Mlle");
	            	JRadioButton mdmButton = new JRadioButton("Mme");
	            	JRadioButton mButton = new JRadioButton("Mr");

	            	civiliteGroup.add(mmeButton);
	            	civiliteGroup.add(mdmButton);
	            	civiliteGroup.add(mButton);

	            	JPanel civilitePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	            	civilitePanel.add(new JPanel().add(mmeButton));
	            	civilitePanel.add(new JPanel().add(mdmButton));
	            	civilitePanel.add(new JPanel().add(mButton));
	            	clientInfoPanel.add(civilitePanel);

	            	clientInfoPanel.add(new JLabel("Nom:"));
	            	clientInfoPanel.add(nomField);
	            	clientInfoPanel.add(new JLabel("Pr�nom:"));
	            	clientInfoPanel.add(prenomField);
	            	clientInfoPanel.add(new JLabel("CIN:"));
	            	clientInfoPanel.add(cinField);

	            	JButton validerButton = new JButton("Valider");
	            	validerButton.addActionListener(new ActionListener() 
	            	{
	                    @Override
	                    public void actionPerformed(ActionEvent e) 
	                    {
	                        String civilite = "";
	                        if (mmeButton.isSelected()) 
	                            civilite = "Mlle";
	                        else if (mdmButton.isSelected()) 
	                            	civilite = "Mme";
	                             else if (mButton.isSelected()) 
	                            	 civilite = "Mr";

	                        String nom = nomField.getText();
	                        String prenom = prenomField.getText();
	                        String cin = cinField.getText();
                        
	                        if (nom.isEmpty() || prenom.isEmpty() || cin.isEmpty() || (!mmeButton.isSelected() && !mdmButton.isSelected() && !mButton.isSelected())) 
	                        {
	                            JOptionPane.showMessageDialog(null,"Veuillez remplir tous les champs et s�lectionner une civilit�.","Erreur de saisie",JOptionPane.ERROR_MESSAGE);
	                            return;
	                        }
	                        Client client = new Client(nom,prenom,cin,civilite);
	                        try 
	                        {
	                        	if (Agence.loueVoiture(client, v)) 
	                        	{
	                        		Object[] options = {"OK"};
	                        		int n = JOptionPane.showOptionDialog(null,"Voiture lou�e avec succ�s!","R�sultat de la location",JOptionPane.PLAIN_MESSAGE,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
	                        		if (n == 0) 
	                        		{
	                        			clientInfoDialog.dispose();
	                        			detailsDialog.dispose(); 
	                        		}
	                        	}
	                                //JOptionPane.showMessageDialog(null,"Voiture lou�e avec succ�s!","R�sultat de la location",JOptionPane.INFORMATION_MESSAGE);
	                            else 
	                                JOptionPane.showMessageDialog(null,"vous avez deja loue une voiture","",JOptionPane.ERROR_MESSAGE);
	                        } 
	                        catch (Exception e1) 
	                        {
	                            e1.printStackTrace();
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
}*/













package TP6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


@SuppressWarnings("serial")
public class RentCarPage extends JPanel 
{
    private InterCritere crit = new InterCritere();
    public String prix;
    public String marque;
    public String modele;
    public String annee;
    public String couleur;
    //private JPanel cards;  
    private JPanel carPanel; 

    public RentCarPage(JPanel cards) 
    {
        //this.cards = cards;  
        JPanel header = new JPanel();
        JPanel criteriaAndButtonPanel = new JPanel();
        criteriaAndButtonPanel.setLayout(new BoxLayout(criteriaAndButtonPanel, BoxLayout.Y_AXIS));

        JPanel criteriaPanel = new JPanel();
        criteriaPanel.add(new JLabel("Prix:"));
        criteriaPanel.add(new JTextField(10));
        criteriaPanel.add(new JLabel("Marque:"));
        criteriaPanel.add(new JComboBox<>(Agence.getDistinctMarquesFromLocations()));
        criteriaPanel.add(new JLabel("Modele:"));
        criteriaPanel.add(new JComboBox<>(Agence.getDistinctModelesFromLocations()));
        criteriaPanel.add(new JLabel("Annee:"));
        criteriaPanel.add(new JTextField(4));
        criteriaPanel.add(new JLabel("Couleur:"));
        criteriaPanel.add(new JComboBox<>(Agence.getDistinctCouleursFromLocations()));

        JButton applyButton = new JButton("Appliquer");
        applyButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                prix = ((JTextField) criteriaPanel.getComponent(1)).getText();
                String marque = (String) ((JComboBox<?>) criteriaPanel.getComponent(3)).getSelectedItem();
                String modele = (String) ((JComboBox<?>) criteriaPanel.getComponent(5)).getSelectedItem();
                String annee = ((JTextField) criteriaPanel.getComponent(7)).getText();
                String couleur = (String) ((JComboBox<?>) criteriaPanel.getComponent(9)).getSelectedItem();

                if (!prix.isEmpty())
                	crit.addCritere(new CriterePrix(Float.parseFloat(prix)));
                if (!"Tous".equals(marque))
                	crit.addCritere(new CritereMarque(marque));
            	if (!"Tous".equals(modele))
            	crit.addCritere(new CritereModele(modele));
                if (!annee.isEmpty())
                	crit.addCritere(new CritereAnneeProd(Integer.parseInt(annee)));
                if (!"Tous".equals(couleur))
                	crit.addCritere(new CritereCouleur(couleur));
                displayFilteredCars();
                crit.viderCritere();
            }
        });
        criteriaAndButtonPanel.add(criteriaPanel);
        criteriaAndButtonPanel.add(applyButton);

        carPanel = new JPanel(); 

        carPanel.setLayout(new GridLayout(0, 3));
        displayFilteredCars();  

        JPanel newPanel = new JPanel(new BorderLayout());
        newPanel.add(header, BorderLayout.NORTH);
        newPanel.add(criteriaAndButtonPanel, BorderLayout.CENTER);
        newPanel.add(carPanel, BorderLayout.SOUTH);

        this.add(newPanel, BorderLayout.CENTER);
    }

    private void displayFilteredCars() 
    {
        List<Voiture> filteredCars = Agence.afficheSelection(crit);
        carPanel.removeAll();  
        if(filteredCars == null)
        {
        	JOptionPane.showMessageDialog(this,"Aucune voiture n'est disponible avec ces criteres. Veuillez selectionner des criteres valides.","",JOptionPane.ERROR_MESSAGE);
        	for (Voiture voiture : Agence.getVoitures()) 
            {
        		if(voiture.getNombreDisponible()>0)
            	{
                JPanel carContainerPanel = new JPanel(new BorderLayout());

                carContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                ImageIcon originalCar = new ImageIcon(voiture.getImage());
                Image carImage = originalCar.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
                JLabel carLabel = new JLabel(new ImageIcon(carImage));
                carContainerPanel.add(carLabel, BorderLayout.CENTER);

                JLabel detailsLabel = new JLabel("<html>Marque: " + voiture.getMarque() + "<br>Mod�le: " + voiture.getModel()+ "<br>Annee: " + voiture.getAnneeProduction() + "</html>");
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
            }
        }
        else
        {
        	for (Voiture voiture : filteredCars) 
            {
            	if(voiture.getNombreDisponible()>0)
            	{
            		JPanel carContainerPanel = new JPanel(new BorderLayout());

                    carContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    ImageIcon originalCar = new ImageIcon(voiture.getImage());
                    Image carImage = originalCar.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
                    JLabel carLabel = new JLabel(new ImageIcon(carImage));
                    carContainerPanel.add(carLabel, BorderLayout.CENTER);

                    JLabel detailsLabel = new JLabel("<html>Marque: " + voiture.getMarque() + "<br>Mod�le: " + voiture.getModel()+ "<br>Prix: " + voiture.getPrixLocation() + "</html>");
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
            }
        }

        carPanel.revalidate();
        carPanel.repaint();
    }
    
    public void showCarDetails(Voiture v) 
    {
    	JDialog detailsDialog = new JDialog();
    	detailsDialog.setTitle("D�tails de la voiture");
    	detailsDialog.setSize(600, 400);

    	JPanel detailsPanel = new JPanel();
    	detailsPanel.setLayout(new BorderLayout());

    	JPanel infoPanel = new JPanel(new GridLayout(9, 2));

    	infoPanel.add(new JLabel("Matricule:"));
    	infoPanel.add(new JLabel(v.getMatricule()));
    	infoPanel.add(new JLabel("Marque:"));
    	infoPanel.add(new JLabel(v.getMarque()));
    	infoPanel.add(new JLabel("Mod�le:"));
    	infoPanel.add(new JLabel(v.getModel()));
    	infoPanel.add(new JLabel("Ann�e de production:"));
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
		                        		int n = JOptionPane.showOptionDialog(null,"Voiture lou�e avec succ�s pour " + client.getCivilite() + ". " + client.getPrenom() + " " + client.getNom() + " !","R�sultat de la location",JOptionPane.PLAIN_MESSAGE,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
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
	        	            	panel1.add(new JLabel("Pr�nom:"));
	        	            	panel1.add(prenomField);            	
	        	            	panel1.add(new JLabel("Civilit�:"));
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
	            		                            JOptionPane.showMessageDialog(null,"Veuillez remplir tous les champs et s�lectionner une civilit�.","Erreur de saisie",JOptionPane.ERROR_MESSAGE);
	            		                            return;
	            		                        }
	            		                        Client client = new Client(nom,prenom,cin,civilite);
	            		                        try 
	            		                        {
	            		                        	if (Agence.loueVoiture(client, v)) 
	            		                        	{
	            		                        		Object[] options = {"OK"};
	            		                        		int n = JOptionPane.showOptionDialog(null,"Voiture lou�e avec succ�s!","R�sultat de la location",JOptionPane.PLAIN_MESSAGE,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
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
}