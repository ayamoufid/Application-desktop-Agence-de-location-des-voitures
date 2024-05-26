//package TP6;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//
//public class LocationVoiture 
//{
//    public static void main(String[] args) 
//    {
//        Scanner scanner = new Scanner(System.in);
//        List<Voiture> voitures = Agence.readVoituresFromFile("voitures");
//        Map<Client, Voiture> locations = Agence.readLocationsFromFile("locations");
//       // List<Client> clients = Agence.readClientsFromFile("clients");
//        //Agence agence = new Agence(voitures,locations,clients);
//        int choix;
//        do  
//        {
//            System.out.println("\n----- Menu -----");
//            System.out.println("1. Affichage des voitures");
//            System.out.println("2. Louer une voiture");
//            System.out.println("3. Afficher les voitures louees");
//            System.out.println("4. Verifier si un client a loue une voiture");
//            System.out.println("5. Verifier si une voiture est louee");
//            System.out.println("6. Rendre une voiture");
//            System.out.println("7. Afficher toutes les locations");
//            System.out.println("8. liste critere disponible");
//            System.out.println("9. Quitter");
//            System.out.print("Choix : ");
//            choix = scanner.nextInt();
//            //Critere critereMarqueRenault = null;
//            switch (choix) 
//            {
//                case 1:
//                	System.out.println("------------ Affichage generale des voitures ------------ :");
//                    agence.affichageGeneralVoiture();
//                    break;
//                case 2:
//                    System.out.println("----- Liste des clients : -----");
//                    Client client = Client.Saisir_Client();
//                    System.out.println("----- Liste des voitures  -----");
//                    List<Voiture> voitures1=Agence.getDisponible(voitures);
//                    for (int i = 0; i < voitures1.size(); i++) 
//                        System.out.println(i + 1 + ". " + voitures1.get(i)); 
//                    System.out.print("Choix de la voiture (num�ro) : ");
//                    int numeroVoiture = scanner.nextInt();
//                    try 
//                    {
//                        Agence.loueVoiture(client, voitures1.get(numeroVoiture - 1));
//                        System.out.println("La voiture a �t� lou�e avec succ�s.");
//                    } 
//                    catch (Exception e) 
//                    {
//                        System.out.println(e.getMessage());
//                    }
//                    break;
//
//                case 3:
//                    System.out.println("------------------ Voitures louees : ---------------");
//                    agence.affichVoituresLouees();
//                    
//                    break;
//                case 4:
//                     client = Client.Saisir_Client();
//                    if (agence.estLoueur(client))
//                        System.out.println("------> Le client a loue une voiture");
//                    else
//                        System.out.println("------> Le client n'a jamais loue une voiture");
//                    break;
//                case 5:
//                    System.out.println("----- Liste des voitures : -----");
//                    for (int i = 0; i < voitures.size(); i++) 
//                        System.out.println(i + 1 + ". " + voitures.get(i)); 
//                    System.out.print("Choix de la voiture (numero) : ");
//                    int choixVoiture = scanner.nextInt();
//                    scanner.nextLine(); 
//                    if (choixVoiture >= 1 && choixVoiture <= voitures.size()) 
//                    {
//                        Voiture voitureVerification = voitures.get(choixVoiture - 1);
//                        if (agence.estLoue(voitureVerification))
//                        	
//                            System.out.println("------> La voiture est actuellement louee");
//                        else
//                            System.out.println("------> La voiture n'est pas louee");
//                    } 
//                    else 
//                        System.out.println("Choix de la voiture invalide.");
//                    break;
//                case 6:
//                	String clientCin = Agence.getCinFromUser();
//                    System.out.println("----- Liste des voitures louees : -----");
//                    Agence.rendVoiture(clientCin);
//                   
//                    break;
//                case 7 : Agence.afficherLocation();
//                		break;
//                case 8 :
//                	boolean exit = false;
//                	  while (!exit) 
//                	  {
//                          System.out.println("\nMenu:");
//                          System.out.println("1. Afficher distinct Marques");
//                          System.out.println("2. Afficher distinct Couleurs");
//                          System.out.println("3. Afficher distinct Ann�e Productions");
//                          System.out.println("4. Afficher distinct Mod�les");
//                          System.out.println("5. Quitter");
//                          System.out.print("Choix : ");
//                          int choice = scanner.nextInt();
//                          scanner.nextLine();
//                          switch (choice) 
//                          {
//                              case 1:
//                                  String[] distinctMarques = Agence.getDistinctMarquesFromLocations();
//                                  System.out.println("Distinct Marques: " + distinctMarques);
//                                  break;
//                              case 2:
//                            	  String[] distinctCouleurs = Agence.getDistinctCouleursFromLocations();
//                                  System.out.println("Distinct Couleurs: " + distinctCouleurs);
//                                  break;
//                              case 3:
//                            	  Integer[] distinctAnneeProductions = Agence.getDistinctAnneeProductionsFromLocations();
//                                  System.out.println("Distinct Ann�e Productions: " + distinctAnneeProductions);
//                                  break;
//                              case 4:
//                            	  String[] distinctModeles = Agence.getDistinctModelesFromLocations();
//                                  System.out.println("Distinct Mod�les: " + distinctModeles);
//                                  break;
//                              case 5:
//                                  exit = true;
//                                  System.out.println("Au revoir !");
//                                  break;
//                              default:
//                                  System.out.println("Choix invalide. Veuillez r�essayer.");
//                          }
//                      }
//                		break;
//                case 9 :
//                    System.out.println("Au revoir !");
//                    break;
//                default:
//                    System.out.println("Choix invalide.");
//            }
//        } 
//        while (choix != 10);
//        scanner.close();
//    }
//}