package mg.itu.prom16.annotation.Attribut;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // Specifie la durée de vie de l'annotation
@Target(ElementType.FIELD) // Specifie où l'annotation peut être utilisé (dans ce cas , sur les classes)
public @interface Email{
    
}
