package br.com.zup.CasaDoCodigo.Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { VerificaCampoDuplicadoValidator.class })
public @interface VerificaCampoDuplicado {

	String message() default "{VerificaCampoDuplicado}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String attribute();

	Class<?> clazz();

}
