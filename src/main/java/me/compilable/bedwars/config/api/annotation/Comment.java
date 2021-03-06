/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used to hint the FormatCompiler to output comments on the correct places
 *
 * @author geNAZt
 * @version 1.0
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Comment {
    String value() default "";
}
