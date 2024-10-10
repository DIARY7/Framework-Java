package mg.itu.prom16.utilitaire;

import java.lang.reflect.Method;

public class VerbMethode {
    String verb;
    Method action;
    
    public VerbMethode(String verb, Method action) {
        this.verb = verb;
        this.action = action;
    }
    public VerbMethode(){
        
    }
    
    public String getVerb() {
        return verb;
    }
    public void setVerb(String verb) {
        this.verb = verb;
    }
    public Method getAction() {
        return action;
    }
    public void setAction(Method action) {
        this.action = action;
    }    
}
