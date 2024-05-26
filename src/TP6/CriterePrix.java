package TP6;

public class CriterePrix implements Critere 
{
    private float prixMax;

    public CriterePrix(float f) 
    {
        this.prixMax = f;
    }

    public boolean estSatisfaitPar(Voiture voiture) 
    {
        return voiture.getPrixLocation() < prixMax;
    }
}
