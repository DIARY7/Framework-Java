package mg.itu.prom16.utilitaire;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.Enumeration;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.prom16.annotation.AnnotAttribut;
import mg.itu.prom16.annotation.GET;
import mg.itu.prom16.annotation.POST;
import mg.itu.prom16.annotation.Param;
import mg.itu.prom16.annotation.RestApi;


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
    public static Object checkParamClass(HttpServletRequest req,Parameter param) throws Exception{
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
}
