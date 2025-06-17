package com.proyectoSpring.fullstack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.core.EvoInflectorLinkRelationProvider;

@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HateoasConfig {

    @Bean
    public LinkRelationProvider linkRelationProvider() {
        return new EvoInflectorLinkRelationProvider();
    }
} 