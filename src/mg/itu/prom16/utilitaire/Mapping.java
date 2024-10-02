package mg.itu.prom16.utilitaire;

import java.lang.reflect.Method;

public class Mapping {
    String className;
    String methodName;
    Method fonction;
    String verb;

    public Mapping(String className, String methodName,Method fonction,String verb) {
        this.className = className;
        try {
            Class c = Class.forName(className);
            this.methodName = methodName;
            this.fonction = fonction;
            this.verb = verb;
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
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public Method getFonction() {
        return fonction;
    }
    public void setFonction(Method fonction) {
        this.fonction = fonction;
    }
    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }    
}
