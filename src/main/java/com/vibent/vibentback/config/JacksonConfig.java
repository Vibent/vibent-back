package com.vibent.vibentback.config;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public JacksonAnnotationIntrospector annotationIntrospector(){
        return new JacksonAnnotationIntrospector() {
            @Override
            public boolean hasIgnoreMarker(final AnnotatedMember m) {
                return m.getDeclaringClass() == Deserializers.Base.class || super.hasIgnoreMarker(m);
            }
        };
    }
}

