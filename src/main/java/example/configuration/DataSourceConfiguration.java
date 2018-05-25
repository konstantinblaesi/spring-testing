package example.configuration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("cloud")
public class DataSourceConfiguration {
    @Bean
    public Cloud cloud() {
        return new CloudFactory().getCloud();
    }

    @Bean
    @ConfigurationProperties("vcap.services.db-storage.credentials")
    public DataSource dataSource() {
        return cloud().getSingletonServiceConnector(DataSource.class, null);
    }
}