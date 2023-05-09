package com.fdherrera.graphqldemo.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.datafaker.Faker;

/**
 * {@link Faker} configuration file.
 *
 * @author federico.herrera
 */
@Configuration
public class DataSourceConfig {
    @Bean
    public Faker faker() {
        return new Faker();
    }
}
