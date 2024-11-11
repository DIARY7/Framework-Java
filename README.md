## Sprint 13
Mettre des anoontation de validation sur les attributs de model comme:
-Numeric
-Email
-Date
-...

public class Emp {
    @AnnotAttribut(name="anarana")    
    String nom;

    @AnnotAttribut(name = "taona")
    @Numeric
    int age;

    @Email
    String email;
    ...



