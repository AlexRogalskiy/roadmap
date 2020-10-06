package com.sensiblemetrics.api.roadmap.commons.general.annotation;

import com.sensiblemetrics.api.roadmap.commons.general.provider.VariableArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(VariableArgumentsProvider.class)
public @interface VariableSource {
    /**
     * The name of the static variable
     */
    String value();
}
