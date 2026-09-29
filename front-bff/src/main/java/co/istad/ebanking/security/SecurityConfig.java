package co.istad.ebanking.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrationRepository) {

        http.authorizeExchange(exchange -> exchange
                .pathMatchers("/team",
                        "/profile/**",
                        "/auth/**").authenticated()
                .anyExchange().permitAll());
        http.csrf(csrfSpec -> csrfSpec.disable());
        http.formLogin(formLoginSpec -> formLoginSpec.disable());

//        http.logout(logoutSpec -> logoutSpec.disable());
        http.httpBasic(httpBasicSpec -> httpBasicSpec.disable());

//        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        http.oauth2Login(Customizer.withDefaults());
//        http.logout(logout -> logout
//                .logoutUrl("/oauth2/authenticated/logout")
//                .logoutSuccessHandler((exchange, authentication) -> {
//                    exchange.getExchange().getResponse()
//                            .setStatusCode(org.springframework.http.HttpStatus.NO_CONTENT);
//                    return exchange.getExchange().getResponse().setComplete();
//                })
//        );
        http.logout(logoutSpec -> logoutSpec.logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository)));

        return http.build();
    }

    //oidc
    private ServerLogoutSuccessHandler oidcLogoutSuccessHandler(ReactiveClientRegistrationRepository clientRegistrationRepository) {
        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");

        return oidcLogoutSuccessHandler;
    }

    //client
    private ServerLogoutSuccessHandler serverLogoutSuccessHandler() {

        RedirectServerLogoutSuccessHandler redirectServerLogoutSuccessHandler =
                new RedirectServerLogoutSuccessHandler();

        final String DEFAULT_LOGOUT_SUCCESS_URL = "/";

        URI logoutSuccessUrl = URI.create(DEFAULT_LOGOUT_SUCCESS_URL);

        redirectServerLogoutSuccessHandler.setLogoutSuccessUrl(logoutSuccessUrl);

        return redirectServerLogoutSuccessHandler;
    }
}
