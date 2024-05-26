package TP6;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

public class Client implements Comparable<Client>
{
	private String nom;
	private String prenom;
	private String cin;
	private String civilite;
	private String dateLocation;
	private String heureLocation;
	
	public Client(String n, String p, String cin, String c)
	{
		nom=n;
		prenom=p;
		this.cin=cin;
		civilite=c;
		dateLocation = LocalDate.now().toString();
		heureLocation = LocalTime.now().toString();
	}
	
    public Client(String nom, String prenom, String cin, String civilite, String dateLocation, String heureLocation) 
    {
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.civilite = civilite;
        this.dateLocation = dateLocation;
        this.heureLocation = heureLocation;
    }
	
	public String getNom()
	{
		return nom;
	}
	
	public String getPrenom()
	{
		return prenom;
	}
	
	public String getCin()
	{
		return cin;
	}
	
	public String getCivilite()
	{
		return civilite;
	}
	
	public String getDateLocation()
	{
		return dateLocation;
	}
	
	public String getHeureLocation()
	{
		return heureLocation;
	}
	
	public void setNom(String n)
	{
		nom = n;
	}
	
	public void setPrenom(String p)
	{
		prenom = p;
	}
	
	public void setCin(String c)
	{
		cin = c;
	}
	
	public void setCivilite(String c)
	{
		civilite = c;
	}
	
	public int compareTo(Client c)
	{
		return cin.compareTo(c.getCin());
    } 
	
	public String toString() 
	{
		return "CIN: "+cin+" Nom et prenom: "+civilite+" "+nom+" "+prenom+"date de location :"+ dateLocation+" "+heureLocation;
	}

	public static Client Saisir_Client() 
	{
		Scanner clavier = new Scanner(System.in);
		String prenom, nom, cin, civilite;
		System.out.println("Donner Le nom du client ");
		nom = clavier.nextLine();
		System.out.println("Donner Le prenom du client ");
		prenom = clavier.nextLine();
		System.out.println("Donner le cin du client");
		cin = clavier.nextLine();
		System.out.println("Donner la civilite du client");
		civilite = clavier.nextLine();
		clavier.close();
		return new Client(nom,prenom,cin, civilite);
	}
}
