package TP6;

import java.util.ArrayList;
import java.util.List;

public class InterCritere implements Critere 
{
    private List<Critere> lesCriteres;

    public InterCritere() 
    {
        this.lesCriteres = new ArrayList<>();
    }

    public void addCritere(Critere c) 
    {
        lesCriteres.add(c);
    }

    public void viderCritere()
	{
		lesCriteres.clear();
	}
    
    public boolean estSatisfaitPar(Voiture v) 
    {
        for (Critere c : lesCriteres) 
            if (!c.estSatisfaitPar(v)) 
                return false;
        return true;
    }
    
}
