package mg.itu.prom16.utilitaire;

import java.util.ArrayList;
import java.util.HashMap;

public class DataAndException {
    HashMap<String,ArrayList<String>> listeException = null;
    HashMap <String,String> listeValeurs = null;
    
    public DataAndException() {
        this.listeException = new HashMap<>();
        this.listeValeurs = new HashMap<>();
    }

    /* Fonction complementaire */
    public void ajouterValeur(String name,String valeur){
        this.listeValeurs.put(name, valeur);
    }
    public void ajouterException(String name, ArrayList<String> valeurException){
        if (valeurException.size()!=0) {
            if (this.listeException.get(name)==null) {
                this.listeException.put(name, new ArrayList<>());
            }
            this.listeException.get(name).addAll(valeurException);    
        }
        
    }

    public void ajouterException(String name,String valeurException){
        if (this.listeException.get(name)==null) {
            this.listeException.put(name, new ArrayList<>());
        }
        this.listeException.get(name).add(valeurException);
    }
    /* getters and setters */
    public HashMap<String, ArrayList<String>> getListeException() {
        return listeException;
    }
    public void setListeException(HashMap<String, ArrayList<String>> listeException) {
        this.listeException = listeException;
    }
    public HashMap<String,String> getListeValeurs() {
        return listeValeurs;
    }
    public void setListeValeurs(HashMap<String,String> listeValeurs) {
        this.listeValeurs = listeValeurs;
    }
    
}
