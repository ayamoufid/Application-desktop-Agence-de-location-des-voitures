package TP6;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.*;
import java.util.*;

import javax.swing.JOptionPane;

public class Agence 
{
    private static List<Voiture> voitures;
    private static Map<Client, Voiture> locations;
    private static List<Client> clients;
    
    public Agence(List<Voiture> voitures) 
    {
        Agence.voitures = voitures;
        Agence.locations = new TreeMap<>();
    }
    
    public Agence(List<Voiture> voitures, Map<Client, Voiture> locations,List<Client> clients) 
    {
    	Agence.voitures = voitures;
        Agence.locations = locations;
        Agence.clients = clients;
    }

    public static Iterator<Voiture> selectionne(Critere c) 
    {
        List<Voiture> result = new ArrayList<>();
        for (Voiture voiture : voitures) 
            if (c.estSatisfaitPar(voiture)) 
            	result.add(voiture);
        return result.iterator();
    }

    public static List<Voiture> afficheSelection(Critere c) 
    {
    	if(c!=null)
    	{
    		List<Voiture> selectedVoitures = new ArrayList<>();
    		Iterator<Voiture> it = selectionne(c); 
    		while (it.hasNext()) 
    		{
    			Voiture v=it.next();
    			if(v.getNombreDisponible()>0)
    			selectedVoitures.add(v);
    		}
    		if(selectedVoitures.size()==0)
    		{
    			System.out.println("0");
    			return null;
    		}
    		System.out.println("good");
    		return selectedVoitures;	
    	}
    	System.out.println("c null");
    	return null;
    }
    
    static List<Voiture> getDisponible(List<Voiture> voitures) 
    {
        List<Voiture> availableCars = new ArrayList<>();
        for (Voiture voiture : voitures) 
            if (voiture.getNombreDisponible() > 0) 
                availableCars.add(voiture);
        return availableCars;
    }

    public static Client findClientByCin(String cin) 
    {
        for (Map.Entry<Client, Voiture> entry : locations.entrySet()) 
        {
            Client client = entry.getKey();
            if (client.getCin().equals(cin)) 
                return client;
        }
        return null;
    }
    
    public static boolean loueVoiture(Client client, Voiture v) throws Exception 
    {
        if (!voitures.contains(v)) return false;
        Client g=findClientByCin(client.getCin());
        if(g!=null)
        	 return false;
        if (locations.containsValue(v)) return false;
        locations.put(client, v);
        v.setNombreDisponible(v.getNombreDisponible()-1);
        writeLocationsToFile(locations);
        writeVoituresToFile(voitures);
        try 
        {
			if(!doesClientExist(client.getCin()))
			{
				clients.add(client);
				addClientToFile("clients",client);
			}
		} 
        catch (Exception e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return true;
    }

    public boolean estLoueur(Client client) 
    {
        return locations.containsKey(client);
    }
    
    public static Map<Client, Voiture> getLocations()
    {
    	return locations;
    }
    
    public boolean estLoue(Voiture v) 
    {
        return locations.containsValue(v);
    }
  
    public static long afficherDifference(Client c) 
    {
        LocalDate dateLocationObj = LocalDate.parse(c.getDateLocation());
        LocalDateTime dateHeureLocation = LocalDateTime.of(dateLocationObj, LocalTime.parse(c.getHeureLocation()));
        LocalDateTime dateHeureActuelles = LocalDateTime.now();
        Duration difference = Duration.between(dateHeureLocation, dateHeureActuelles);
        long heuresDifference = difference.toHours();
        return heuresDifference;
    }
        
    public static String getCinFromUser() 
    {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Enter the Client's Cin:");
        String cin = scanner1.nextLine();
        scanner1.close();
        return cin;
    }

    public static boolean rendVoiture(String cin) 
    {
        Client client = findClientByCin(cin);
        if (client != null) 
        {
            System.out.println(client);
            Voiture v = locations.get(client);

            if (v != null) 
            {
                v.setNombreDisponible(v.getNombreDisponible() + 1);
                locations.remove(client);
                afficherLocation();
                writeLocationsToFile(locations);
                writeVoituresToFile(voitures);
                return true;
            } 
            else 	System.out.println("No voiture lou�e");
        } 
        else
            System.out.println("No client found with the given Cin");
        return false;
    }

    public Iterator<Voiture> lesVoituresLouees() 
    {
        return locations.values().iterator();
    }
    
    public void affichVoituresLouees()
    {
		Iterator<Voiture> iter_voitures = this.lesVoituresLouees();
		while (iter_voitures.hasNext())
			System.out.println(iter_voitures.next());
	}
    
    public void afficherVoitures() 
    {
		for(Voiture v : voitures)
			System.out.println(v);
	}
    
    public void affichageGeneralVoiture()
	{
		int key,key2;
		InterCritere crit=new InterCritere();
		Scanner scan = new Scanner(System.in);
		do
		{
			System.out.println("1-Afficher toutes les voitures");
			System.out.println("2-afficher les voitures sous 1 seul critere");
			System.out.println("3-afficher les voitures sous plusieurs criteres");
			System.out.println("4-Quitter");
			System.out.print("Choix : ");
			key = scan.nextInt();
			switch (key) 
			{
				case 1 : afficherVoitures();
						break;
				case 2 : 
					do
					{
						System.out.println("1- pour critere Prix ");
						System.out.println("2- pour critere Marque ");
						System.out.println("3- pour critere Annee ");
						System.out.println("4- pour critere modele");
						System.out.println("5- pour critere couleur");
						System.out.println("0- pour quitter ");
						System.out.print("Choix : ");
						key2 = scan.nextInt();
						switch (key2) 
						{
							case 1 : 	
								System.out.println("Entrer le prix critere ");
								crit.addCritere(new CriterePrix(Voiture.convertInt(scan.next())));		
							    break;
							case 2 : 
								System.out.println("Entrer la marque critere ");
								crit.addCritere(new CritereMarque(scan.next()));
								break;		
							case 3 : 
								System.out.println("Entrer l'annee  critere ");
								crit.addCritere(new CritereAnneeProd(scan.nextInt()));
								break;
							case 4 : 
								System.out.println("Entrer modele critere ");
								crit.addCritere(new CritereModele(scan.next()));
								break;
							case 5 : 
								System.out.println("Entrer couleur critere ");
								crit.addCritere(new CritereCouleur(scan.next()));
								break;
							case 0: break;
							default :
								System.out.println("choix invalide: " + key2);
								
						}
						if(key2!=0)
						{
						//afficheSelection(crit);
						crit.viderCritere();
						}
					}
					while (key2 != 0);
					break;
				case 3 : 
					do 
					{
						System.out.println("1- pour ajouter critere Prix ");
						System.out.println("2- pour ajouter critere Marque ");
						System.out.println("3- pour ajouter critere Annee ");
						System.out.println("4- pour ajouter critere Modele ");
						System.out.println("5- pour ajouter critere couleur ");
						System.out.println("0- pour afficher selon les critere choisis ");
						System.out.print("Choix : ");
						key2 = scan.nextInt();
						switch (key2) 
						{
							case 1 :	
								System.out.println("Entrer le prix critere ");
								crit.addCritere(new CriterePrix(Voiture.convertInt(scan.next())));	
								break;
							case 2 :
								System.out.println("Entrer la marque critere ");
								crit.addCritere(new CritereMarque(scan.next()));
								break;
							case 3 :
								System.out.println("Entrer l'annee  critere ");
								crit.addCritere(new CritereAnneeProd(Voiture.convertInt(scan.next())));
								break;
							case 4 :
								System.out.println("Entrer la marque critere ");
								crit.addCritere(new CritereModele(scan.next()));
								break;
							case 5 :
								System.out.println("Entrer la couleur critere ");
								crit.addCritere(new CritereCouleur(scan.next()));
								break;
							case 0 : break;
							default :
								System.out.println("choix invalide: " + key2);
								
						}
					}
					while(key2 != 0);
					//afficheSelection(crit);
					crit.viderCritere();
					break;
				case 4 : break;
				default :
					System.out.println("choix invalide: ");
			}
		}
		while(key != 4);
	}
    
    public static void afficherLocation() 
    {
		locations.forEach(
				(k, v) -> System.out.println("---------------------\nClient: \n\t" + k + "\nVoiture loue: \n\t" + v));
	}
    
    public static boolean containsV(final String matricule) 
    {
		return voitures.stream().anyMatch(o -> matricule.equals(o.getMatricule()));
	}
    
    public static List<Voiture> getVoitures() 
    {
        return voitures;
    }
    public static List<Client> getClients() 
    {
        return clients;
    }
    public static void ajouterVoiture(Voiture v) 
    {
		try 
		{
			if (containsV(v.getMatricule())) throw new Exception();
			else 
				{
					voitures.add(v);
					JOptionPane.showMessageDialog(null, "Voiture ajoutee avec succes", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Le matricule : (" + v.getMatricule() + ") correspond a une voiture deja existante", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void supprimerVoiture(String v) 
	{
		try 
		{
			if (!voitures.removeIf(vt -> vt.getMatricule().equals(v)))
				throw new Exception();
			else
				System.out.println("suppression avec succes");
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Le matricule : (" + v + ") ne correspond a aucune voiture", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static List<Voiture> getVoituresDisponibles(List<Voiture> voitures) 
	{
        List<Voiture> voituresDisponibles = new ArrayList<>();
        for (Voiture voiture : voitures) 
            if (voiture.getNombreDisponible() > 0)
                voituresDisponibles.add(voiture);
        return voituresDisponibles;
    }
	
	static List<Voiture> readVoituresFromFile(String filePath) 
	{
        List<Voiture> voitures = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                if (parts.length == 9) 
                { 
                    Voiture voiture = new Voiture(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            Integer.parseInt(parts[3].trim()),
                            Float.parseFloat(parts[4].trim()),
                            parts[5].trim(),
                            Integer.parseInt(parts[6].trim()),
                            Integer.parseInt(parts[7].trim()),
                            parts[8].trim()
                    );
                    voitures.add(voiture);
                } 
                else 
                    System.out.println("Invalid line in the file: " + line);
            }
        } 
        catch (IOException | NumberFormatException e) 
        {
            e.printStackTrace();
        }
        return voitures;
    }
	
	
	static Map<Client, Voiture> readLocationsFromFile(String filePath) 
	{
        Map<Client, Voiture> locations = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                if (parts.length == 15) 
                { 
                    Client client = new Client(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            parts[3].trim(),
                            parts[4].trim(),
                            parts[5].trim()
                    );

                    Voiture voiture = new Voiture(
                            parts[6].trim(),
                            parts[7].trim(),
                            parts[8].trim(),
                            Integer.parseInt(parts[9].trim()),
                            Float.parseFloat(parts[10].trim()),
                            parts[11].trim(),
                            Integer.parseInt(parts[12].trim()),
                            Integer.parseInt(parts[13].trim()),
                            parts[14].trim()
                    );
                    locations.put(client, voiture);
                } 
                else 
                    System.out.println("Invalid line in the file: " + line);
            }
        } 
        catch (IOException | NumberFormatException e) 
        {
            e.printStackTrace(); 
        }
        return locations;
    }
	
	static void writeVoituresToFile(List<Voiture> voitures) 
	{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("voitures"))) 
        {
            for (Voiture voiture : voitures) 
            {
                String line = String.format("%s,%s,%s,%d,%.2f,%s,%d,%d,%s",
                        voiture.getMatricule(),
                        voiture.getMarque(),
                        voiture.getModel(),
                        voiture.getAnneeProduction(),
                        voiture.getPrixLocation(),
                        voiture.getCouleur(),
                        voiture.getNombre(),
                        voiture.getNombreDisponible(),
                        voiture.getImage()
                );
                writer.write(line);
                writer.newLine();
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace(); 
        }
    }
	
	static void writeLocationsToFile(Map<Client, Voiture> locations) 
	{
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("locations"))) 
	    {
	        for (Map.Entry<Client, Voiture> entry : locations.entrySet()) 
	        {
	            Client client = entry.getKey();
	            Voiture voiture = entry.getValue();
	            String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%d,%.2f,%s,%d,%d,%s",
	                    client.getNom(),
	                    client.getPrenom(),
	                    client.getCin(),
	                    client.getCivilite(),
	                    client.getDateLocation(),
	                    client.getHeureLocation(),
	                    voiture.getMatricule(),
	                    voiture.getMarque(),
	                    voiture.getModel(),
	                    voiture.getAnneeProduction(),
	                    voiture.getPrixLocation(),
	                    voiture.getCouleur(),
	                    voiture.getNombre(),
	                    voiture.getNombreDisponible(),
	                    voiture.getImage()
	            );
	            writer.write(line);
	            writer.newLine();
	        }
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace(); 
	    }
	}

	public static String[] getDistinctMarquesFromLocations() 
	{
	    Set<String> distinctMarques = new HashSet<>();
	    distinctMarques.add("Tous");
	    for (Voiture voiture : voitures) 
	    	if(voiture.getNombreDisponible()>0)
	        distinctMarques.add(voiture.getMarque());
	    
	    return distinctMarques.toArray(new String[0]);
	}

	public static String[] getDistinctCouleursFromLocations() 
	{
	    Set<String> distinctCouleurs = new HashSet<>();
	    distinctCouleurs.add("Tous");
	    for (Voiture voiture : voitures) 
	    	if(voiture.getNombreDisponible()>0)
	        distinctCouleurs.add(voiture.getCouleur());
	    return distinctCouleurs.toArray(new String[0]);
	}

	public static Integer[] getDistinctAnneeProductionsFromLocations() 
	{
	    Set<Integer> distinctAnneeProductions = new HashSet<>();
	    for (Voiture voiture : voitures) 
	    	if(voiture.getNombreDisponible()>0)
	        distinctAnneeProductions.add(voiture.getAnneeProduction());
	    return distinctAnneeProductions.toArray(new Integer[0]);
	}

	public static String[] getDistinctModelesFromLocations() 
	{
	    Set<String> distinctModeles = new HashSet<>();
	    distinctModeles.add("Tous");
	    for (Voiture voiture : voitures) {
	    	if(voiture.getNombreDisponible()>0)
	        distinctModeles.add(voiture.getModel());
	    }
	    return distinctModeles.toArray(new String[0]);
	}
	
	public static Voiture findCarByMatricule(String matricule) 
	{
	    for (Voiture voiture : voitures) 
	    {
	        if (voiture.getMatricule().equals(matricule)) {
	            return voiture;
	        }
	    }
	    return null;
	}

    public static void updateLocations(String matricule, Voiture v2) 
    {
        for (Map.Entry<Client, Voiture> entry : locations.entrySet()) 
        {
            if (entry.getValue().getMatricule().equals(matricule)) 
                entry.setValue(v2);
        }
        writeLocationsToFile(locations);
    }

    public static void updateLocations1(String cni, Client newClient) 
    {
        Map<Client, Voiture> updatedLocations = new HashMap<>(locations);

        for (Map.Entry<Client, Voiture> entry : locations.entrySet()) 
        {
            if (entry.getKey().getCin().equals(cni)) 
            {
                updatedLocations.remove(entry.getKey());
                updatedLocations.put(newClient, entry.getValue());
            }
        }
        locations = updatedLocations;
        writeLocationsToFile(locations);
    }


    
	static List<Client> readClientsFromFile(String filePath) 
	{
        List<Client> clients = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                if (parts.length == 4) 
                { 
                    Client client = new Client(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            parts[3].trim()    
                    );
                    clients.add(client);
                } 
                else 
                    System.out.println("Invalid line in the file: " + line);
            }
        } 
        catch (IOException | NumberFormatException e) 
        {
            e.printStackTrace();
        }
        return clients;
    }
		
	public static boolean doesClientExist(String cniToCheck) 
	{
        for (Client client : clients) 
        {
            if (client.getCin().equals(cniToCheck)) 
                return true; 
        }
        return false; 
    }
		
	public static Client findClientByCNI(String cniToCheck) 
	{
        for (Client client : clients) 
        {
            if (client.getCin().equals(cniToCheck)) 
            {
                return client; 
            }
        }
        return null; 
    }
	
    public static void addClientToFile(String filePath, Client client) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) 
        {
            String line = client.getNom() + "," + client.getPrenom() + "," +
                          client.getCin() + "," + client.getCivilite();
            writer.write(line);
            writer.newLine();
            System.out.println("Client added to file successfully.");
        } 
        catch (IOException e) 
        {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
    

    public static void deleteClientByCNI(String filePath, String cniToDelete) 
    {
        File inputFile = new File(filePath);
        File tempFile = new File("tempClients");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) 
        {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) 
            {
                if (!currentLine.contains(cniToDelete + ",")) 
                    writer.write(currentLine + System.getProperty("line.separator"));
            }

        } 
        catch (IOException e) 
        {
            System.err.println("Error reading/writing to the file: " + e.getMessage());
        }
        try 
        {
            Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Client with CNI " + cniToDelete + " deleted successfully.");
        } 
        catch (IOException e) 
        {
            System.out.println("Error deleting client with CNI " + cniToDelete + ": " + e.getMessage());
        }
    }

	public static void setClients(List<Client> readClientsFromFile) 
	{
		clients = readClientsFromFile;		
	}
}