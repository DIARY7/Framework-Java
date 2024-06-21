## Sprint 7
Mettre l'annotation @AnnotAttribut(name="name_dans_input") sur les attributs de classe des models

exemple:(Model emp) 
import mg.itu.prom16.annotation.AnnotAttribut;

public class Emp {
    @AnnotAttribut(name="anarana")    
    String nom;

    @AnnotAttribut(name = "taona")
    int age;

    @AnnotAttribut(name="daty_nahaterahana")
    LocalDate dateNaissance;
}

(Controlleur)
    @Get("/classParam")
    public ModelView methodeclassParam ( @Param(name = "date_embauche") LocalDate dateEmbauche , @Param(name = "employe") Emp emp ){
        ModelView mv = new ModelView();
        mv.addObject("employe", emp);
        mv.addObject("dateEmbauche", dateEmbauche);
        mv.setUrl("page/formulaire.jsp");
        return mv;
    }

Pour passer les valeurs de input vers le controlleur 
//Dans formulaire
//Annotation (Priorite) 
<input type="text" name="emp.anarana" placeholder="Entrez le nom de l'employé" >
//Nom
<input type="text" name="emp.nom" placeholder="Entrez le nom de l'employé" >



