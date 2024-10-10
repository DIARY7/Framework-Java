package mg.itu.prom16.utilitaire;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Mapping {
    String className;
    // String methodName;
    // Method fonction;
    // String verb;
    /* Sprint 10 modifier */
    ArrayList<VerbMethode> verbeMethodes;
    /*--------------------- */
    public Mapping(String className, String methodName,Method fonction,String verb) {
        this.className = className;
        this.verbeMethodes=new ArrayList<VerbMethode>();
        try {
            VerbMethode vm = new VerbMethode(verb, fonction);
            this.verbeMethodes.add(vm);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public Mapping() {
    
    }
    
    
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public boolean isSameVerb(String verb){
        for (int i = 0; i < verbeMethodes.size(); i++) {
            if (verb.compareToIgnoreCase(verbeMethodes.get(i).getVerb())==0) {
                return true;
            }
        }
        return false;
    } 

    public ArrayList<VerbMethode> getVerbeMethodes() {
        return verbeMethodes;
    }

    public void setVerbeMethodes(ArrayList<VerbMethode> verbeMethodes) {
        this.verbeMethodes = verbeMethodes;
    }   
    public Method getMethodExec(String verb){
        for (int i = 0; i < this.verbeMethodes.size(); i++) {
            if (this.verbeMethodes.get(i).getVerb().compareToIgnoreCase(verb)==0) {
                return this.verbeMethodes.get(i).getAction();
            }
        }
        return null;
    }
}
