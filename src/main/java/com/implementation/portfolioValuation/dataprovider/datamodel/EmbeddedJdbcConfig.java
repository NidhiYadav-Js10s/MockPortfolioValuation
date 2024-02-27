package com.implementation.portfolioValuation.dataprovider.datamodel;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.beans.BeanProperty;

@EnableAutoConfiguration
public class EmbeddedJdbcConfig {
    @BeanProperty
    public DataSource dataSource() {
        try {
            var dbBuilder = new EmbeddedDatabaseBuilder();
            return dbBuilder.setType(EmbeddedDatabaseType.H2)
                    .addScripts("classpath:schema.sql", "classpath:data.sql")
                    .build();
        } catch (Exception e) {
            System.out.println("Embedded DataSource bean cannot be created!");
            return null;
        }
    }
}
