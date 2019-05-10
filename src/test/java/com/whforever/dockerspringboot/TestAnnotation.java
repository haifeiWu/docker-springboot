package com.whforever.dockerspringboot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface TestAnnotation {
    String   value() default "";
    String   name();
    int      age();
    String[] newNames();
}
