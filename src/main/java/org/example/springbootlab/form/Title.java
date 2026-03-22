package org.example.springbootlab.form;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@NotNull
@Size(min=2, max=200, message = "Please enter a title of a movie")
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface Title {

    String message() default "Please enter a title of a movie";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
