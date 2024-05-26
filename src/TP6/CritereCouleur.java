package TP6;

public class CritereCouleur implements Critere 
{
    private String couleur;
    
    public CritereCouleur(String couleur)
    {
        this.couleur = couleur;
    }
    
    public boolean estSatisfaitPar(Voiture voiture) 
    {
        return voiture.getCouleur().equals(couleur);
    }
}