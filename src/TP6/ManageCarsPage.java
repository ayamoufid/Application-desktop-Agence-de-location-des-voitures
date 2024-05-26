package TP6;

import javax.swing.*;	
import java.awt.*;
import java.awt.event.*;

public class ManageCarsPage extends JPanel 
{
    private CardLayout cl;
    private JPanel cards;

    public ManageCarsPage() 
    {
        setLayout(new BorderLayout());
        String[] menuItems = {"Ajouter voiture", "Modifier voiture", "Supprimer voiture","Afficher voiture"};
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
        JPanel card1 = new AddCarForm();
        cards.add(card1, "Ajouter voiture");
        JPanel card2 = new ModifyCarForm();
        cards.add(card2, "Modifier voiture");
        JPanel card3 = new DeleteCarForm();
        cards.add(card3, "Supprimer voiture");
        JPanel card4 = new AfficherVoitureForm();
        cards.add(card4, "Afficher voiture");
        add(cards, BorderLayout.CENTER);
    }
}
