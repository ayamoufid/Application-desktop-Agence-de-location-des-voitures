package TP6;

import javax.swing.*;	
import java.awt.*;
import java.awt.event.*;
public class ManageClientsPage extends JPanel  
{
	    private CardLayout cl;
	    private JPanel cards;

	    public ManageClientsPage() 
	    {
	        setLayout(new BorderLayout());
	        String[] menuItems = {"Ajouter client", "Modifier client", "Supprimer client","Afficher client"};
	        JToolBar toolBar = new JToolBar();
	        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
	        for (String item : menuItems) 
	        {
	            JButton button = new JButton(item);
	            button.addActionListener(new ActionListener() 
	            {
	                @Override
	                public void actionPerformed(ActionEvent e) 
	                {
	                    cl.show(cards, e.getActionCommand());
	                }
	            });
	            toolBar.add(button);
	            toolBar.add(Box.createHorizontalGlue());
	        }
	        add(toolBar, BorderLayout.NORTH);

	        cards = new JPanel(new CardLayout());
	        cl = (CardLayout)(cards.getLayout());
	        JPanel card1 = new AddClientForm();
	        cards.add(card1, "Ajouter client");
	        JPanel card2 = new ModifyClientForm();
	        cards.add(card2, "Modifier client");
	        JPanel card3 = new DeleteClientForm();
	        cards.add(card3, "Supprimer client");
	        JPanel card4 = new AfficherClientForm();
	        cards.add(card4, "Afficher client");
	        add(cards, BorderLayout.CENTER);
	  }
}