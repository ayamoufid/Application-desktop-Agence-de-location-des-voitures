package TP6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ReturnCarPage extends JPanel 
{
	public ReturnCarPage() 
    {
        JPanel cinPanel = new JPanel();
        JTextField cinTextField = new JTextField(10);
        cinPanel.add(new JLabel("Entrer votre CIN:"));
        cinPanel.add(cinTextField);
        JButton returnButton = new JButton("Rendre");
        returnButton.addActionListener((ActionEvent e) -> 
        {
            String cin = cinTextField.getText();
            if (!validateCIN(cin)) 
            {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un CIN valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }        
            long h = 0;
            Voiture voiture = null;
            Client client = Agence.findClientByCin(cin);
            if(client != null)
            {
            	h = Agence.afficherDifference(Agence.findClientByCin(cin));
                voiture = Agence.getLocations().get(Agence.findClientByCin(cin));
            }
            if (Agence.rendVoiture(cin)) 
                JOptionPane.showMessageDialog(this, "La voiture est rendue. Merci pour votre visite.\nCIN: " + cin + "\n le nombre heure de location : " + h + " prix:" + h * voiture.getPrixLocation());
            else 
                JOptionPane.showMessageDialog(this, "Pas de voiture à rendre ou CIN invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(returnButton);
        
        JPanel newPanel = new JPanel(new BorderLayout());
        newPanel.add(cinPanel, BorderLayout.CENTER);
        newPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(newPanel, BorderLayout.CENTER);
    }

    private boolean validateCIN(String cin)
    {
    	return !cin.isEmpty();
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            JFrame frame = new JFrame("Return Car Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.add(new ReturnCarPage());
            frame.setVisible(true);
        });
    }
}

