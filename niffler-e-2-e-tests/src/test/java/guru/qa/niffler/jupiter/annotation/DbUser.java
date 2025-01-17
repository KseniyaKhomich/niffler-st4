package guru.qa.niffler.jupiter.annotation;

import guru.qa.niffler.jupiter.extension.DBUserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import guru.qa.niffler.jupiter.extension.CreateUserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith({DBUserExtension.class, CreateUserExtension.class})
public @interface DbUser {
  String username() default "";

  String password() default "";
}
