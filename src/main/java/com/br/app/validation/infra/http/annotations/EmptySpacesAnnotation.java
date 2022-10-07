package com.br.app.validation.infra.http.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmptySpacesValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EmptySpacesAnnotation {
    public String message() default "Password cannot have empty spaces";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
