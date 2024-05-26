package TP6;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class FenetreAdmin extends JFrame 
{
    public FenetreAdmin(String adminName) 
    {
    	   SwingUtilities.invokeLater(() -> 
           {
               JFrame frame = new JFrame("Agence Aya de gestion de voitures");
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

               JPanel header = new JPanel(new BorderLayout());
               ImageIcon originalLogo = new ImageIcon("admin.png");
               Image logoImage = originalLogo.getImage().getScaledInstance(300, 100, Image.SCALE_DEFAULT);
               JLabel logo = new JLabel(new ImageIcon(logoImage));
               
               JLabel agencyName = new JLabel("Ayaa's Cars", SwingConstants.CENTER);
               agencyName.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 50));
               agencyName.setForeground(Color.ORANGE);
               
               header.add(logo, BorderLayout.WEST);
               header.add(agencyName, BorderLayout.CENTER);
               
               JPanel cards = new JPanel(new CardLayout());
               
               JToolBar toolBar = new JToolBar();
               toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
               String[] menuItems = {"    Gestion voitures    ", "    Gestion clients    ", "    Afficher Location    "}; 
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
               JPanel card4 = new ManageCarsPage(); 
               cards.add(card4, "    Gestion voitures    ");
               JPanel card5 = new ManageClientsPage(); 
               cards.add(card5, "    Gestion clients    ");
               frame.add(header, BorderLayout.CENTER);
               
               JPanel card6 = new AfficherLocationForm();
               cards.add(card6, "    Afficher Location    ");
               frame.add(header, BorderLayout.NORTH);
               
               JScrollPane scrollPane = new JScrollPane(cards);
               frame.add(scrollPane, BorderLayout.CENTER);
               frame.setVisible(true);
           });
       }
}
