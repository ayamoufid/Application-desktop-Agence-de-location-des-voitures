package TP6;

public class CritereAnneeProd implements Critere 
{
    private int anneeProduction;
    public CritereAnneeProd(int annee)
    {
        this.anneeProduction = annee;
    }
    public boolean estSatisfaitPar(Voiture voiture) 
    {
        return voiture.getAnneeProduction() == anneeProduction;
    }

}
