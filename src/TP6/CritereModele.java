package TP6;

public class CritereModele implements Critere 
{
    private String modele;
    
    public CritereModele(String modele)
    {
        this.modele = modele;
    }
    
    public boolean estSatisfaitPar(Voiture voiture) 
    {
        return voiture.getModel().equals(modele);
    }
}
