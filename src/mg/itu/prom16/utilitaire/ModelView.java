package mg.itu.prom16.utilitaire;

import java.util.HashMap;

public class ModelView {
    String url;
    HashMap<String,Object> data;
    String urlError;

    public ModelView(){
        data = new HashMap<>();
    }
    public void addObject(String cle,Object object){
        this.data.put(cle,object);   
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public HashMap<String, Object> getData() {
        return data;
    }
    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
    public String getUrlError() {
        return urlError;
    }
    public void setUrlError(String urlError) {
        this.urlError = urlError;
    }
}
