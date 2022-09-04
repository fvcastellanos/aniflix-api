package com.aniflix.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.aniflix.api.model.repository")
public class RepositoryConfiguration {
}
