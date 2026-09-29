package kh.edu.istad.identity.security;

import jakarta.annotation.PostConstruct;
import kh.edu.istad.identity.domain.Role;
import kh.edu.istad.identity.domain.User;
import kh.edu.istad.identity.feature.oath2.JpaRegisteredClientRepository;
import kh.edu.istad.identity.feature.role.RoleRepository;
import kh.edu.istad.identity.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityInit {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JpaRegisteredClientRepository jpaRegisteredClientRepository;

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUuid(UUID.randomUUID().toString());
            user.setUsername("pich");
            user.setPassword(passwordEncoder.encode("qwer"));
            user.setEmail("sokunsreypichsam@gmail.com");
            user.setDob(LocalDate.of(1998, 9, 9));
            user.setGender("Female");
            user.setProfileImage("profile3.jpg");
            user.setCoverImage("cover.jpg");
            user.setFamilyName("Sam");
            user.setGivenName("Sokunsreypich");
            user.setPhoneNumber("077459947");
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setIsEnabled(true);

            // Assign role to user
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRole("SUPER_ADMIN"));
            roles.add(roleRepository.findByRole("USER"));
            user.setRoles(roles);

            userRepository.save(user);
            log.info("User has been saved: {}", user.getId());
        }
    }

    @PostConstruct
    void initOAuth2() {

        TokenSettings tokenSettings = TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .accessTokenTimeToLive(Duration.ofDays(3))
                .reuseRefreshTokens(false) // refresh token rotation
                .refreshTokenTimeToLive(Duration.ofDays(5))
                .build();

        ClientSettings clientSettings = ClientSettings.builder()
                .requireProofKey(true)
                .requireAuthorizationConsent(false)
                .build();

        var itpStandard = RegisteredClient.withId("itp-standard")
                .clientId("itp-standard")
                .clientSecret(passwordEncoder.encode("qwerqwer")) // store in secret manager
                .scopes(scopes -> {
                    scopes.add(OidcScopes.OPENID); // required!
                    scopes.add(OidcScopes.PROFILE);
                    scopes.add(OidcScopes.EMAIL);
                })
                .redirectUris(uris -> {
                    uris.add("http://localhost:15999/login/oauth2/code/itp-standard");
                    uris.add("http://localhost:9090/login/oauth2/code/itp-standard");
                    uris.add("http://localhost:9090");
                    uris.add("https://cstad.edu.kh/");
                })
                .postLogoutRedirectUris(uris -> {
                    uris.add("http://localhost:9090");
                })
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC) //TODO: grant_type:client_credentials, client_id & client_secret, redirect_uri
                .authorizationGrantTypes(grantTypes -> {
                    grantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                    grantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
                    grantTypes.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
                })
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();

        var itpFrontBff = RegisteredClient.withId("itp-front-bff")
                .clientId("itp-front-bff")
                .clientSecret(passwordEncoder.encode("qwerqwer")) // store in secret manager
                .scopes(scopes -> {
                    scopes.add(OidcScopes.OPENID); // required!
                    scopes.add(OidcScopes.PROFILE);
                    scopes.add(OidcScopes.EMAIL);
                })
                .redirectUris(uris -> {
                    uris.add("http://localhost:15990/login/oauth2/code/itp-front-bff");
                    uris.add("http://localhost:15990");

                })
                .postLogoutRedirectUris(uris -> {
                    uris.add("http://localhost:15990");
                })
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC) //TODO: grant_type:client_credentials, client_id & client_secret, redirect_uri
                .authorizationGrantTypes(grantTypes -> {
                    grantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                })
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();

        var itpAdminBff = RegisteredClient.withId("itp-admin-bff")
                .clientId("itp-admin-bff")
                .clientSecret(passwordEncoder.encode("qwerqwer")) // store in secret manager
                .scopes(scopes -> {
                    scopes.add(OidcScopes.OPENID); // required!
                    scopes.add(OidcScopes.PROFILE);
                    scopes.add(OidcScopes.EMAIL);
                })
                .redirectUris(uris -> {
                    uris.add("http://localhost:15991/login/oauth2/code/itp-admin-bff");
                    uris.add("http://localhost:15991");

                })
                .postLogoutRedirectUris(uris -> {
                    uris.add("http://localhost:15991");
                })
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC) //TODO: grant_type:client_credentials, client_id & client_secret, redirect_uri
                .authorizationGrantTypes(grantTypes -> {
                    grantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                })
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();

        RegisteredClient registeredClient = jpaRegisteredClientRepository.findByClientId("itp-standard");
        log.info("Registered client: {}", registeredClient);
        RegisteredClient registeredClient2 = jpaRegisteredClientRepository.findByClientId("itp-front-bff");
        log.info("Registered client: {}", registeredClient2);
        RegisteredClient registeredClient3 = jpaRegisteredClientRepository.findByClientId("itp-admin-bff");
        log.info("Registered client: {}", registeredClient3);

        if (registeredClient == null) {
            jpaRegisteredClientRepository.save(itpStandard);
        }
        if (registeredClient2 == null) {
            jpaRegisteredClientRepository.save(itpFrontBff);
        }
        if (registeredClient3 == null) {
            jpaRegisteredClientRepository.save(itpAdminBff);
        }

    }

}
