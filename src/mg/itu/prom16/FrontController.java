package mg.itu.prom16;
import mg.itu.prom16.annotation.*;
import mg.itu.prom16.utilitaire.CustomSession;
import mg.itu.prom16.utilitaire.Mapping;
import mg.itu.prom16.utilitaire.ModelView;
import mg.itu.prom16.utilitaire.Outil;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;

import java.net.URL;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {
    // Ho an'ny sprint 1,2
    ArrayList<String> listController;
    HashMap<String,Mapping> dicoMapping = new HashMap<String,Mapping>() ;//Clé ny URL ,d mapping ny value
    String baseUrl;
    String erreur=null; // Sprint5

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        PrintWriter out= resp.getWriter();
        
        if (erreur!=null) {
            out.println(erreur);
            return;
        }
        boolean existMapping = false;
        String urlTaper = req.getRequestURL().toString().split(baseUrl)[1];
        for (String key : dicoMapping.keySet()) {
            if (key.compareTo(urlTaper)==0) {
                existMapping = true;
            }
        }
       
        if (existMapping) {
            try {
                Object value = invoqueMethode(dicoMapping.get(urlTaper),req);
                ModelViewtoJsp(req,resp,value); // Sprint 4    
            } catch (Exception e) {
                // TODO: handle exception
                out.println(e.getMessage());
                e.printStackTrace();
            }
                         
        }
        else{    
            out.println("URL introuvable");
        }
        
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            
            String name_package = getServletConfig().getInitParameter("packageController");
            
            this.baseUrl = getServletConfig().getInitParameter("baseUrl");
            this.listController =  this.getCtrlInPackage(name_package);
            
            if (this.listController.size()==0) {    
                throw new Exception("Il n'y a aucun controller dans "+name_package);
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            this.erreur = e.getMessage();
        }
        
    }
    
    /* sprint1  */
    public ArrayList<String> getCtrlInPackage( String name_package) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = name_package.replace(".", "/");
        URL packageUrl = classLoader.getResource(path);
        System.out.println("Le path url "+packageUrl);
        ArrayList<String> liste = new ArrayList<>();
        if (packageUrl==null) {
            
            throw new Exception("Le package "+ name_package +" n'existe pas");
        }
        if (packageUrl!=null) {
            File packageFile = new File(packageUrl.getFile());
            
            if (packageFile.exists() && packageFile.isDirectory()) {
                File[] files = packageFile.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().endsWith(".class")) {
                        String temp_name_classe = files[i].getName().substring(0, files[i].getName().lastIndexOf("."));
                        Class temp_class = Class.forName( name_package +"."+ temp_name_classe);
                        if (temp_class.getAnnotation(AnnotationCtrl.class)!=null) {
                            liste.add(temp_name_classe);
                            setDicoMapping(temp_class); // Sprint 2
                        }
                        
                    }
                }
            }
           
            
        }
    
        return liste; 
        
    }
    /* sprint2 */
    public void setDicoMapping(Class c) throws Exception {
        Method[] methodes = c.getMethods();
        System.out.println("Le nombre des methodes dans  "+ c.getSimpleName() + " est : "+methodes.length);
        for (int j = 0; j < methodes.length; j++) {
            Get annotGet = methodes[j].getAnnotation(Get.class); 
            if ( annotGet !=null ) {
                if (dicoMapping.get(annotGet.value())!=null) {
                    throw new Exception("Duplication de GetMapping "+annotGet.value());
                }
                dicoMapping.put(annotGet.value(), new Mapping( c.getName() , methodes[j].getName(),methodes[j]));
            }
        }
    }
    /* sprint3 */
    private Object invoqueMethode(Mapping map,HttpServletRequest req) throws Exception {
        /* misy sprint 6 */
        Class c =  Class.forName(map.getClassName());
        //Method meth = Outil.searchMethod(map.getMethodName(), c);
        Method meth = map.getFonction();
        Parameter[] parameters = meth.getParameters();
       
        Object[] arguments= new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            
            /* sprint 8 */
            if (parameters[i].getType()==CustomSession.class) {
                arguments[i] = new CustomSession(req.getSession());
                continue;
            }

            //Alea
            if(parameters[i].getAnnotation(Param.class)==null) {
                throw new Exception("Le parametre "+parameters[i].getName() + " doit être annotée");
            }
            
            //sprint 7
            Object class_obj = Outil.checkParamClass(req, parameters[i]);
            if (class_obj!=null) {
                arguments[i] = class_obj; 
            }
            // else if (req.getParameter(parameters[i].getName())!=null) {
            //     arguments[i] = Outil.parseParam(parameters[i], req.getParameter(parameters[i].getName()));
            // }
            if (parameters[i].getAnnotation(Param.class)!=null) {
                if(req.getParameter(parameters[i].getAnnotation(Param.class).name())!=null){
                    arguments[i] = Outil.parseParam(parameters[i], req.getParameter(parameters[i].getAnnotation(Param.class).name()));
                }    
            }
            
            
        }
        
        return meth.invoke(c.getDeclaredConstructor().newInstance(),arguments);
    }
    /* sprint 4 */
    private void ModelViewtoJsp(HttpServletRequest req, HttpServletResponse resp,Object value) throws Exception {
        if (value instanceof String) {
            resp.getWriter().println(value.toString());
        }
        else if (value instanceof ModelView) 
        {
            ModelView mv = (ModelView) value;
            HashMap<String, Object> dico = mv.getData();
            for (String key : dico.keySet()) {
                req.setAttribute(key, dico.get(key));
            }
            RequestDispatcher dispat = req.getRequestDispatcher("/"+mv.getUrl());
            dispat.forward(req,resp);
        }
        else{
            throw new Exception("Valeur de retour non Valide");
        }
        
    } 
    
}