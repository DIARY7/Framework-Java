package mg.itu.prom16.utilitaire;


import jakarta.servlet.http.HttpSession;

/* sprint 8  */
public class CustomSession {
    HttpSession httpSession;
    public CustomSession(){
        
    }
    public CustomSession(HttpSession httpSession){
        this.httpSession = httpSession;
    }
    public Object get(String cle){
        return this.httpSession.getAttribute(cle);
    } 
    public void add(String cle,Object object){
        if (this.httpSession.getAttribute(cle)==null) {
            this.httpSession.setAttribute(cle, object);    
        }
    }
    public void update(String cle,Object object){
        this.httpSession.setAttribute(cle, object);
    }
    public void remove(String cle){
        this.httpSession.removeAttribute(cle);
    }
    public void destroy(){
        this.httpSession.invalidate();
    }

    /* setters and getters */
    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
    
}
