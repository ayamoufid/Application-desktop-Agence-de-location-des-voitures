package TP6;

public interface Critere 
{
	/**
	* @param v la voiture dont on teste la confirmit�
	* @return true si et seulement si la voiture est conforme au
	* crit�re (on dit que v satisfait le crit�re)
	*/
	public boolean estSatisfaitPar(Voiture v);

}
