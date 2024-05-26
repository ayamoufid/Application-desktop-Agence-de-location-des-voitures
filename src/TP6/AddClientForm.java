package TP6;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class AddClientForm extends JPanel 
{
    private JTextField cniField;
    private JTextField nameField;
    private JTextField surnameField;
    private JRadioButton mrRadio;
    private JRadioButton mrsRadio;
    private JRadioButton missRadio;

    public AddClientForm() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        // CNI
        JLabel cniLabel = new JLabel("CNI:");
        cniField = new JTextField(20);
        formPanel.add(cniLabel, gbc);
        gbc.gridx++;
        formPanel.add(cniField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Name
        JLabel nameLabel = new JLabel("Nom:");
        nameField = new JTextField(20);
        formPanel.add(nameLabel, gbc);
        gbc.gridx++;
        formPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Surname
        JLabel surnameLabel = new JLabel("Prenom:");
        surnameField = new JTextField(20);
        formPanel.add(surnameLabel, gbc);
        gbc.gridx++;
        formPanel.add(surnameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Civility
        JLabel civilityLabel = new JLabel("Civilite:");
        formPanel.add(civilityLabel, gbc);
        gbc.gridx++;

        mrRadio = new JRadioButton("Monsieur");
        mrsRadio = new JRadioButton("Madame");
        missRadio = new JRadioButton("Mademoiselle");

        ButtonGroup civilityGroup = new ButtonGroup();
        civilityGroup.add(mrRadio);
        civilityGroup.add(mrsRadio);
        civilityGroup.add(missRadio);

        JPanel civilityPanel = new JPanel();
        civilityPanel.add(mrRadio);
        civilityPanel.add(mrsRadio);
        civilityPanel.add(missRadio);

        formPanel.add(civilityPanel, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Submit Button
        JButton submitButton = new JButton("Ajouter");
        submitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (areAllFieldsFilled()) 
                {
                    String cni = cniField.getText();
                    String name = nameField.getText();
                    String surname = surnameField.getText();
                    String civility = getCivility();
                    if(Agence.doesClientExist(cni))
                    	 JOptionPane.showMessageDialog(null, "Client existe.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    else 
                    {
                    	Client cl=new Client(name,surname,cni,civility);
                    	Agence.addClientToFile("clients",cl);
                    	Agence.getClients().add(cl);
                    	JOptionPane.showMessageDialog(null, "Client ajouter avec succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                    }
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
        return !cniField.getText().isEmpty() &&
               !nameField.getText().isEmpty() &&
               !surnameField.getText().isEmpty() &&
               (mrRadio.isSelected() || mrsRadio.isSelected() || missRadio.isSelected());
    }

    private void clearFields() 
    {
        cniField.setText("");
        nameField.setText("");
        surnameField.setText("");
        mrRadio.setSelected(false);
        mrsRadio.setSelected(false);
        missRadio.setSelected(false);
    }

    private String getCivility() 
    {
        if (mrRadio.isSelected()) 
            return "Mr";
        else if (mrsRadio.isSelected()) 
            	return "Mme";
             else if (missRadio.isSelected()) 
            	     return "Mlle";
                  else 
                	  return "";
    }
}
