package TP6;

public class CritereMarque implements Critere 
{
    private String marque;
    public CritereMarque(String marque)
    {
        this.marque = marque;
    }
    public boolean estSatisfaitPar(Voiture voiture) 
    {
        return voiture.getMarque().equals(marque);
    }
}
