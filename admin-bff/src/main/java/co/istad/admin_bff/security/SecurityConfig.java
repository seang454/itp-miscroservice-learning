package co.istad.admin_bff.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http.authorizeExchange(exchange -> exchange
                .anyExchange().authenticated());
        http.csrf(csrfSpec -> csrfSpec.disable());
        http.formLogin(formLoginSpec -> formLoginSpec.disable());
//        http.logout(logoutSpec -> logoutSpec.disable());
        http.httpBasic(httpBasicSpec -> httpBasicSpec.disable());

//        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        http.oauth2Login(Customizer.withDefaults());
        return http.build();
    }
}
