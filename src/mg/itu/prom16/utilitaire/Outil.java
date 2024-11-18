package mg.itu.prom16.utilitaire;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.prom16.annotation.AnnotAttribut;
import mg.itu.prom16.annotation.GET;
import mg.itu.prom16.annotation.POST;
import mg.itu.prom16.annotation.Param;
import mg.itu.prom16.annotation.RestApi;
import mg.itu.prom16.annotation.Attribut.Email;
import mg.itu.prom16.annotation.Attribut.Numeric;
import mg.itu.prom16.annotation.Attribut.Required;
import mg.itu.prom16.annotation.Attribut.Date;


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
    
    /* sprint 7 */
    public static Object checkParamClass(HttpServletRequest req,Parameter param,DataAndException dataException) throws Exception{
        Enumeration<String> params = req.getParameterNames();
        boolean exist = false;
        String beforePoint = "";
        while (params.hasMoreElements()) {
            // Récupération du nom du paramètre
            String paramName = params.nextElement();
            if (paramName.contains(".")) {
                if ( param.getName().compareToIgnoreCase(paramName.split("[.]")[0]) == 0 ) {
                    beforePoint = paramName.split("[.]")[0];
                    exist = true;
                    break;
                }
                if (param.getAnnotation(Param.class).name().compareToIgnoreCase(paramName.split("[.]")[0])==0) {
                    beforePoint = paramName.split("[.]")[0];
                    exist = true;
                    break;
                }        
            }
            
        }
        if (exist) {
            Class c = param.getType();
            Field[] attributs = c.getDeclaredFields();
            Object ob = c.getDeclaredConstructor().newInstance();
            for (int i = 0; i < attributs.length; i++) {
                String nameColonne = (attributs[i].getName().charAt(0)+"").toUpperCase() + attributs[i].getName().substring(1);
                Method setter = c.getDeclaredMethod("set"+nameColonne,attributs[i].getType());
                String name = attributs[i].getName();
                if (attributs[i].getAnnotation(AnnotAttribut.class)!=null) {
                    String name_in_annot = attributs[i].getAnnotation(AnnotAttribut.class).name();
                    if (req.getParameter(beforePoint+"."+name_in_annot)!=null) {
                        name = name_in_annot;
                    }
                }
                
                String value = req.getParameter(beforePoint+"."+name);
                // Sprint 13 , Sprint 14
                if (dataException!=null) {
                    dataException.ajouterValeur(beforePoint+"."+name, value);
                    checkTypeAttribut(attributs[i],value,beforePoint+"."+name,dataException);    
                }
                //
                setter.invoke(ob, parseToClass(attributs[i].getType(),value)); 
            }
            return ob;    
        }
        return null;
        
    }
    /* sprint 6 */
    public static Object setParam(HttpServletRequest req, HttpServletResponse resp) {

        return null;
    }
    public static Object parseParam(Parameter param,String value){
        try {
            if (param.getType()==int.class) {
                return Integer.parseInt(value);
            }
            if (param.getType()==double.class) {
                return Double.parseDouble(value);
            }
                
        } catch (Exception e) {
            // TODO: handle exception
            return 0;
        }
        try {
            if (param.getType()==LocalDate.class){
                return LocalDate.parse(value);
            }
            if (param.getType()==String.class) {
                return value;    
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
                
    }
    public static Object parseToClass(Class c,String value){
        try {
            if (c==int.class) {
                return Integer.parseInt(value);
            }
            if (c==double.class) {
                return Double.parseDouble(value);
            }    
        } catch (Exception e) {
            // TODO: handle exception
            return 0;
        }
        try {
            if (c==LocalDate.class){
                return LocalDate.parse(value);
            }
            if (c==String.class) {
                return value;    
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
                
    }
    /* sprint 9 */
    public static Object returnIfGson(Method m,Object value){
        if (m.isAnnotationPresent(RestApi.class)) {
           Gson gson = new Gson();
           return gson.toJson(value);
        }
        return null;
        
    }
    /* sprint 10 */
    public static String getVerb(Method meth) throws Exception {
        String verb="GET";
        if (meth.isAnnotationPresent(POST.class)) {
            verb = "POST";
        }
        if (meth.isAnnotationPresent(GET.class)) {
            verb = "GET";
        }
        // if (verb==null) {
        //     throw new Exception("La methode "+ meth.getName() + " ne contenant pas de method");
        // }
        return verb;
    }
    /* Sprint 13 et Sprint 14 */
    public static void checkTypeAttribut(Field attribut,String valeur,String nameParameter,DataAndException dataException) throws Exception {
        
        ArrayList<String> listeException = new ArrayList<>(); 
        if (attribut.isAnnotationPresent(Required.class)) {
            if (valeur.trim().isEmpty()) {
                System.out.println("D tena nande tato le "+attribut.getName());
                listeException.add("L'attribut "+attribut.getName()+" ne doit pas être null");
            }
            
        }

        if (attribut.isAnnotationPresent(Numeric.class)) {
            try {
                Integer.parseInt(valeur);
                Double.parseDouble(valeur);
                
            } catch (Exception e) {
                // TODO: handle exception
                listeException.add("La valeur doit être de type numeric pour "+attribut.getName());
            }
        }
        if (attribut.isAnnotationPresent(Email.class)) {
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
            if (!valeur.matches(emailRegex)) {
                listeException.add("Email invalide pour l'attribut "+attribut.getName());
            }
        }
        if (attribut.isAnnotationPresent(Date.class)) {
            try {
                LocalDate.parse(valeur);
            } catch (Exception e) {
                // TODO: handle exception
                listeException.add("Date invalide sur l'attribut "+attribut.getName());
            }
        }
        dataException.ajouterException(nameParameter, listeException);
        
    }
    /* Sprint 14 */
    public static void setErreurAndException(HttpServletRequest req , DataAndException dataException){
        
        // Parcourir toutes les clés
        for (String key : dataException.getListeException().keySet()) {
            String chaineErreur = "";
            for (int i = 0; i < dataException.getListeException().get(key).size(); i++) {
                chaineErreur+=dataException.getListeException().get(key).get(i)+";";
            }
            System.out.println("Chaine erreur = "+chaineErreur);
            req.setAttribute("error_"+key, chaineErreur);
        }

        for (String key : dataException.getListeValeurs().keySet()) {
            req.setAttribute("default_"+key, dataException.getListeValeurs().get(key));
        }
    }
}
