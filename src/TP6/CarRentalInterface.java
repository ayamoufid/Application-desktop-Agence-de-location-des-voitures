package TP6;

import javax.imageio.ImageIO;
import javax.swing.*;	
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CarRentalInterface 
{
    public static void main(String[] args) 
    {
    	List<Voiture> voitures = Agence.readVoituresFromFile("voitures");
        Map<Client, Voiture> locations = Agence.readLocationsFromFile("locations");
        List<Client> clients = Agence.readClientsFromFile("clients");
        new Agence(voitures,locations,clients);
        SwingUtilities.invokeLater(() -> 
        {
            JFrame frame = new JFrame("Agence Aya de location de voitures");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            JPanel header = new JPanel(new BorderLayout());
            ImageIcon originalLogo = new ImageIcon("logo.jpg");
            Image logoImage = originalLogo.getImage().getScaledInstance(300, 100, Image.SCALE_DEFAULT);
            JLabel logo = new JLabel(new ImageIcon(logoImage));
            
            JLabel agencyName = new JLabel("Ayaa's Cars", SwingConstants.CENTER);
            agencyName.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 50));
            agencyName.setForeground(Color.ORANGE);
            
            header.add(logo, BorderLayout.WEST);
            header.add(agencyName, BorderLayout.CENTER);
            
           
            JLabel adminLabel = new JLabel("Admin    ");
            adminLabel.setFont(new Font("Arial", Font.BOLD, 14));
            
            adminLabel.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseClicked(MouseEvent e) 
                {
                	showPasswordPanel();
                }

                @Override
                public void mouseEntered(MouseEvent e) 
                {
                    adminLabel.setForeground(Color.BLUE);
                }

                @Override
                public void mouseExited(MouseEvent e) 
                {
                    adminLabel.setForeground(Color.BLACK);
                }
            });
            header.add(adminLabel, BorderLayout.EAST);
           
            JPanel cards = new JPanel(new CardLayout());
      
            JToolBar toolBar = new JToolBar();
            toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
            //String[] menuItems = {"         Accueil           ", "        Louer voiture     ", "    Rendre voiture    ","    Gestion voitures    ", "    Gestion clients    "}; 
            String[] menuItems = {"         Accueil           ", "        Louer voiture     ", "    Rendre voiture    "}; 
            for (String item : menuItems) 
            {
                JButton button = new JButton(item);
                button.addActionListener(new ActionListener() 
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        CardLayout cl = (CardLayout)(cards.getLayout());
                        cl.show(cards, e.getActionCommand());
                    }
                });
                button.addMouseListener(new MouseAdapter() 
                {
                    @Override
                    public void mouseEntered(MouseEvent e) 
                    {
                        button.setBackground(Color.BLUE);
                        button.setForeground(Color.WHITE);
                    }
                    @Override
                    public void mouseExited(MouseEvent e) 
                    {
                        button.setBackground(UIManager.getColor("control"));
                        button.setForeground(Color.BLACK);
                    }
                });
                toolBar.add(button);
                toolBar.add(Box.createHorizontalGlue());
            }
            header.add(toolBar, BorderLayout.SOUTH);

            JPanel card1 = new Acceuil(cards);
            cards.add(card1, "         Accueil           ");
            JPanel card2 = new RentCarPage(cards); 
            cards.add(card2, "        Louer voiture     ");
            JPanel card3 = new ReturnCarPage(); 
            cards.add(card3, "    Rendre voiture    ");
           // JPanel card4 = new ManageCarsPage(); 
            //cards.add(card4, "    Gestion voitures    ");
            //JPanel card5 = new ManageClientsPage(); 
            //cards.add(card5, "    Gestion clients    ");
            frame.add(header, BorderLayout.NORTH);
            JScrollPane scrollPane = new JScrollPane(cards);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
    
    private static void showPasswordPanel() 
    {
        JFrame passwordFrame = new JFrame("Password Panel");
        passwordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        PasswordPanel passwordPanel = new PasswordPanel();
        passwordFrame.getContentPane().add(passwordPanel);

        passwordFrame.pack();
        passwordFrame.setLocationRelativeTo(null);
        passwordFrame.setVisible(true);
    }
}

class Admin 
{
    private static String[] usernames = {"aya", "admin2"};
    private static String[] passwords = {"aya", "password2"};

    public static boolean isAdmin(String username, String password) 
    {
        for (int i = 0; i < usernames.length; i++) 
        {
            if (usernames[i].equals(username) && passwords[i].equals(password))  return true;
        }
        return false;
    }
}

class PasswordPanel extends JPanel 
{
    private JPasswordField passwordField;
    private JTextField loginField;
    private JLabel showPasswordLabel;
    private JButton sendButton;

    PasswordPanel() 
    {
        setLayout(new GridLayout(3, 3, 5, 5));

        JLabel loginLabel = new JLabel("Login");
        JLabel passwordLabel = new JLabel("Mot de passe");

        loginField = new JTextField(20);
        passwordField = new JPasswordField(20);
        passwordField.setEchoChar('*');

        showPasswordLabel = new JLabel(createResizedIcon("eyeno.png", 20, 20));
        showPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            public void mouseClicked(java.awt.event.MouseEvent evt) 
            {
                togglePasswordVisibility();
            }
        });

        sendButton = new JButton("Envoyer");
        sendButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                sendButtonClicked();
            }
        });

        add(loginLabel);
        add(loginField);
        add(new JLabel());
        add(passwordLabel);
        add(passwordField);
        add(showPasswordLabel);
        add(new JLabel());
        add(new JLabel());
        add(sendButton);

        DocumentListener documentListener = new DocumentListener() 
        {
            @Override
            public void insertUpdate(DocumentEvent e) 
            {
                updateFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) 
            {
                updateFields();
            }
        };

        loginField.getDocument().addDocumentListener(documentListener);
        passwordField.getDocument().addDocumentListener(documentListener);
    }

    private void updateFields() 
    {
        char[] password = passwordField.getPassword();
        String login = loginField.getText();
    }

    private void togglePasswordVisibility() 
    {
        if (passwordField.getEchoChar() == 0) 
        {
            passwordField.setEchoChar('*');
            showPasswordLabel.setIcon(createResizedIcon("eyeno.png", 20, 20));
        } 
        else 
        {
            passwordField.setEchoChar((char) 0);
            showPasswordLabel.setIcon(createResizedIcon("eyeyes.png", 20, 20));
        }
    }

    private void sendButtonClicked() 
    {
        char[] password = passwordField.getPassword();
        String login = loginField.getText();
        if (password.length == 0 || login.isEmpty()) 
            JOptionPane.showMessageDialog(null, "Les champs ne peuvent pas etre vides.", "Erreur", JOptionPane.ERROR_MESSAGE);
        else 
        {
        	if (Admin.isAdmin(login, new String(password))) 
        			new FenetreAdmin(login);
            else 
            JOptionPane.showMessageDialog(this, "Vous n'etes pas administrateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ImageIcon createResizedIcon(String imagePath, int width, int height) 
    {
        try 
        {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}