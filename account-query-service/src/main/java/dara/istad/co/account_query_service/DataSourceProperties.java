package dara.istad.co.account_query_service;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Getter
@Setter
@NoArgsConstructor
public class DataSourceProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
