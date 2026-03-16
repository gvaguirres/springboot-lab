package org.example.springbootlab.form;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@NotNull
@Size(min=2, max=200)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface Title {

    String message() default "{A valid movie must be not empty and have size 2 to 200}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
