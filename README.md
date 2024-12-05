## Sprint 14
Asina setUrlError raha misy error , url an'ilay page andehanana raha misy erreur
ex: 
@POST
    @GetMapping("/traite_classParam")
    public ModelView methodeMitovy ( @Param(name = "date_embauche") LocalDate dateEmbauche , @Param(name = "emp") Emp emp ){
        ModelView mv = new ModelView();
        mv.addObject("employe", emp);
        mv.addObject("dateEmbauche", dateEmbauche);
        mv.setUrlError("/classParam");
        mv.setUrl("page/success14.jsp");
        return mv;
    }

.Maka an'ilay valeur par defaut any @ JSP
default_nomClass.attribut

.Maka an'ireo erreur any @ JSP
error_nomClass.attribut




