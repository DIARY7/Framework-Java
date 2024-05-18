package mg.itu.prom16;
import mg.itu.prom16.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.URI;
import java.net.URL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class FrontController extends HttpServlet {
    // Ho an'ny sprint 1
    boolean checked = false ; 
    ArrayList<String> listController;

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
        
        out.println("Voici les listes des controller");
        for (int i = 0; i < listController.size() ; i++) {
            out.println(listController.get(i));
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            if (!checked) {
                String name_package = getServletConfig().getInitParameter("packageController");
                this.listController =  this.getCtrlInPackage(name_package);
                this.checked = true;
            }    
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
    /* sprint1  */
    public ArrayList<String> getCtrlInPackage( String name_package) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = name_package.replace(".", "/");
        URL packageUrl = classLoader.getResource(path);
        ArrayList<String> liste = new ArrayList<>();
        if (packageUrl!=null) {
            URI packageUri = packageUrl.toURI();
            File packageFile = new File(packageUrl.getFile());
            //  = new File(packageUrL);
            if (packageFile.exists() && packageFile.isDirectory()) {
                File[] files = packageFile.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().endsWith(".class")) {
                        String temp_name_classe = files[i].getName().substring(0, files[i].getName().lastIndexOf("."));
                        Class temp_class = Class.forName( name_package +"."+ temp_name_classe);
                        if (temp_class.getAnnotation(AnnotationCtrl.class)!=null) {
                            liste.add(temp_name_classe);
                        }
                    }
                }
            }
            
        }
    
        return liste; 
        
    }
}