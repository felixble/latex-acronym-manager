package de.felixble.abbreviation.ui;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Felix on 05.01.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String cmd() default "";

    String usage() default "";

    String[] defaultVal() default "";
}
