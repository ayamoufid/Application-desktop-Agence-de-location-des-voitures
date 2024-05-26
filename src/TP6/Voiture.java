package TP6;

import java.util.Scanner;

public class Voiture 
{
	private String matricule;
    private String marque;
    private String model;
    private int anneeProduction;
    private float prixLocation;
    private String couleur;
    private int nombre;
    private int nombreDisponible;
    private String image;

    public Voiture(String matricule, String marque, String model, int anneeProduction, float prixLocation,String couleur,int nombre,String image) 
    { 
    	this.matricule = matricule;
        this.marque = marque;
        this.model = model;
        this.anneeProduction = anneeProduction;
        this.prixLocation = prixLocation;
        this.couleur = couleur;
        this.nombre=nombre;
        nombreDisponible=nombre;
        this.image=image;
    }
    
    public Voiture(String matricule, String marque, String model, int anneeProduction, float prixLocation,String couleur,int nombre,int dp,String image) 
    { 
    	this.matricule = matricule;
        this.marque = marque;
        this.model = model;
        this.anneeProduction = anneeProduction;
        this.prixLocation = prixLocation;
        this.couleur = couleur;
        this.nombre=nombre;
        nombreDisponible=dp;
        this.image=image;
    }
    
    public String getMatricule() 
    {
        return matricule;
    }

    public void setMatricule(String mat) 
    {
        this.matricule = mat;
    }
    
    public String getImage() 
    {
        return image;
    }

    public void Image(String image) 
    {
        this.image = image;
    }

    public String getMarque() 
    {
        return marque;
    }

    public void setMarque(String marque) 
    {
        this.marque = marque;
    }

    public String getModel() 
    {
        return model;
    }

    public void setModel(String m) 
    {
        this.model = m;
    }

    public int getAnneeProduction() 
    {
        return anneeProduction;
    }
    
    public int getNombre() 
    {
        return nombre;
    }

    public void setAnneeProduction(int a) 
    {
        this.anneeProduction = a;
    }

    public float getPrixLocation() 
    {
        return prixLocation;
    }
    
    public void setNombre(int nombre) 
    {
        this.nombre=nombre;
    }
    
    public void setNombreDisponible(int nombreDisponible) 
    {
        this.nombreDisponible=nombreDisponible;
    }
    
    public int getNombreDisponible() 
    {
        return nombreDisponible;
    }
    public void setPrixLocation(int p) 
    {
        this.prixLocation = p;
    }
    
    public boolean equals(Voiture v) 
    {
        return matricule.equals(v.matricule) && marque.equals(v.marque) && model.equals(v.model) && anneeProduction == v.anneeProduction && prixLocation == v.prixLocation && v.couleur==v.couleur;   
    }
    
    public String getCouleur() 
    {
        return couleur;
    }

    public void setCouleur(String couleur) 
    {
        this.couleur = couleur;
    }

    public String toString() 
    {
        return "Voiture{" +
                "Matricule='" + matricule + '\'' +
                ", Marque='" + marque + '\'' +
                ", Mod1le='" + model + '\'' +
                ", Ann1e de Production=" + anneeProduction +
                ", Prix de Location=" + prixLocation +
                ", Couleurs=" + couleur +
                ",Nonbre de voiture: "+nombre+
        		",Nombre disponible: "+nombreDisponible+
        		",Image :"+image+
                '}';
    }
    
    public static Integer convertInt(String chaine)
    {
	    try 
	    {
	    	return Integer.parseInt(chaine);
	    }
		catch(Exception e) 
	    {
		    System.out.println("Probleme de convertion de : "+chaine);
		    return 0;
	    }
    }
    
    public boolean equalsByMatricule(Voiture v) 
    {
        return matricule.equals(v.matricule);
    }

    public static Voiture Saisir_Voiture()
	{
		Scanner clavier = new Scanner(System.in);
		String marque, nom, matricule,image,couleur;
		int prix, annee,nombre;
		System.out.println("Donner Le matricule du voiture ");
		matricule = clavier.nextLine();
		System.out.println("Donner La marque du voiture ");
		marque = clavier.nextLine();
		System.out.println("Donner Le nom du modele du voiture ");
		nom = clavier.nextLine();
		System.out.println("Donner le prix du voiture");
		prix =convertInt(clavier.nextLine());
		System.out.println("Donner l'annee de production du voiture");
		annee = convertInt(clavier.nextLine());
		System.out.println("Donner la liste des couleurs de la voiture (séparées par des virgules): ");
		//String couleursStr = 
	    clavier.nextLine();
	    couleur = clavier.nextLine();
	    System.out.println("Donner le nombre existe voiture");
		nombre =convertInt(clavier.nextLine());
		System.out.println("Donner url du voiture ");
		image = clavier.nextLine();
	    return new Voiture(matricule, marque, nom, annee, prix, couleur,nombre,image);
	}
    
    public void modifierVoiture() 
    {
        int key;
        Scanner scan = new Scanner(System.in);
        do 
        {
            System.out.println("1-changer le matricule");
            System.out.println("2-changer la marque");
            System.out.println("3-changer le modele");
            System.out.println("4-changer l'annee de production");
            System.out.println("5-changer le prix");
            System.out.println("6-changer la couleur");
            System.out.println("7-changer nombre");
            System.out.println("0-retourner au menu principal");
            key = scan.nextInt();

            switch (key) 
            {
                case 1:
                    System.out.println("Entrer le nouveau matricule: ");
                    setMatricule(scan.nextLine());
                    break;
                case 2:
                    System.out.println("Entrer la nouvelle marque: ");
                    setMarque(scan.nextLine());
                    break;
                case 3:
                    System.out.println("Entrer le nouveau modele: ");
                    setModel(scan.nextLine());
                    break;
                case 4:
                    System.out.println("Entrer la nouvelle annee de production: ");
                    setAnneeProduction(convertInt(scan.nextLine()));
                    break;
                case 5:
                    System.out.println("Entrer le nouveau prix: ");
                    setPrixLocation(convertInt(scan.nextLine()));
                    break;
                case 6:
                    System.out.println("Entrer la couleur ");
                    setCouleur(scan.nextLine());
                    break;
                case 7:
                    System.out.println("Entrer le nombre: ");
                    setNombre(convertInt(scan.nextLine()));
                    break;
                case 0:
                    System.out.println("Retour au menu principal");
                    break;
                default:
                    System.out.println("Choix incorrect!!!!");
            }
        } while (key != 0);
    }

}
