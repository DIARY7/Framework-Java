package mg.itu.prom16.utilitaire;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Outil {
    public static Method searchMethod(String nameMethode,Class c){
        Method[] methods = c.getMethods();
        Method cible=null; // Sode mbola ho hisy if manko avy eo 
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().compareToIgnoreCase(nameMethode)==0 ) {
                cible = methods[i];
                break;
            }
        }
        return cible;
    }
    public static Object setParam(HttpServletRequest req, HttpServletResponse resp) {

        return null;
    }
    public static Object parseParam(Parameter param,String value){
        if (param.getType()==int.class) {
            return Integer.parseInt(value);
        }
        if (param.getType()==double.class) {
            return Double.parseDouble(value);
        }
        if (param.getType()==LocalDate.class){
            return LocalDate.parse(value);
        }
        return value;
                
    }
}
