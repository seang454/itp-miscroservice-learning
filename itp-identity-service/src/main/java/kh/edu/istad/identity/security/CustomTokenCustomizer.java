package kh.edu.istad.identity.security;

import kh.edu.istad.identity.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

//@Component
//@Slf4j
//public class CustomTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
//
//    @Override
//    public void customize(JwtEncodingContext context) {
//        log.info("=== Token Customizer Called ===");
//        log.info("Token Type: {}", context.getTokenType().getValue());
//
//        Authentication principal = context.getPrincipal();
//        log.info("Principal: {}", principal);
//        log.info("Principal class: {}", principal.getClass().getName());
//        log.info("Principal.getPrincipal() class: {}", principal.getPrincipal().getClass().getName());
//
//        Object principalObj = principal.getPrincipal();
//
//        if (!(principalObj instanceof CustomUserDetails)) {
//            log.warn("Principal is NOT CustomUserDetails, it is: {}", principalObj.getClass().getName());
//            return;
//        }
//
//        CustomUserDetails userDetails = (CustomUserDetails) principalObj;
//        log.info("CustomUserDetails found - Username: {}, UUID: {}, Email: {}",
//                userDetails.getUsername(), userDetails.getUuid(), userDetails.getEmail());
//
//        // For ID Token
//        if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
//            log.info("=== Customizing ID Token ===");
//
//            context.getClaims()
//                    .claim("sub", userDetails.getUsername())
//                    .claim("preferred_username", userDetails.getUsername())
//                    .claim("uuid", userDetails.getUuid())
//                    .claim("email", userDetails.getEmail())
//                    .claim("email_verified", true)
//                    .claim("family_name", userDetails.getFamilyName())
//                    .claim("given_name", userDetails.getGivenName())
//                    .claim("name", userDetails.getGivenName() + " " + userDetails.getFamilyName())
//                    .claim("phone_number", userDetails.getPhoneNumber())
//                    .claim("phone_number_verified", true)
//                    .claim("profile_image", userDetails.getProfileImage())
//                    .claim("picture", userDetails.getProfileImage())
//                    .claim("gender", userDetails.getGender());
//
//            var authorities = userDetails.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .collect(Collectors.toList());
//            context.getClaims().claim("authorities", authorities);
//            context.getClaims().claim("roles", authorities);
//
//            log.info("ID Token customized - Claims added: uuid={}, email={}, family_name={}, given_name={}",
//                    userDetails.getUuid(), userDetails.getEmail(),
//                    userDetails.getFamilyName(), userDetails.getGivenName());
//        }
//
//        // For Access Token
//        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
//            log.info("=== Customizing Access Token ===");
//
//            context.getClaims()
//                    .claim("uuid", userDetails.getUuid())
//                    .claim("email", userDetails.getEmail())
//                    .claim("family_name", userDetails.getFamilyName())
//                    .claim("given_name", userDetails.getGivenName())
//                    .claim("phone_number", userDetails.getPhoneNumber())
//                    .claim("profile_image", userDetails.getProfileImage())
//                    .claim("gender", userDetails.getGender());
//
//
//            var authorities = userDetails.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .collect(Collectors.toList());
//            context.getClaims().claim("authorities", authorities);
//
//            log.info("Access Token customized - UUID: {}", userDetails.getUuid());
//        }
//    }
//}

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final UserRepository userRepository;

//    @Override
//    public void customize(JwtEncodingContext context) {
//
//        // Only customize ID + Access tokens
//        if (!OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())
//                && !OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
//            return;
//        }
//
//        String username = context.getPrincipal().getName();
//        log.info("Customizing token for user: {}", username);
//
//        var user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found: " + username));
//
//        context.getClaims()
//                .claim("uuid", user.getUuid())
//                .claim("email", user.getEmail())
//                .claim("family_name", user.getFamilyName())
//                .claim("given_name", user.getGivenName())
//                .claim("phone_number", user.getPhoneNumber())
//                .claim("profile_image", user.getProfileImage())
//                .claim("cover_image", user.getCoverImage())
//                .claim("gender", user.getGender());
//    }
@Override
public void customize(JwtEncodingContext context) {

    // 1. Get the Grant Type
    String grantType = context.getAuthorizationGrantType().getValue();

    // 2. Handle Client Credentials (No User involved)
    if ("client_credentials".equals(grantType)) {
        log.info("Customizing token for Client: {}", context.getPrincipal().getName());
        // You can add specific client claims here if you want
        context.getClaims().claim("client_id", context.getPrincipal().getName());
        return; // Exit early, do not look for a user!
    }

    // 3. Handle User-based flows (Authorization Code, etc.)
    if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())
            || OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {

        String username = context.getPrincipal().getName();
        log.info("Customizing token for User: {}", username);

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        context.getClaims()
                .claim("uuid", user.getUuid())
                .claim("email", user.getEmail())
                .claim("family_name", user.getFamilyName())
                .claim("given_name", user.getGivenName())
                .claim("phone_number", user.getPhoneNumber())
                .claim("profile_image", user.getProfileImage())
                .claim("cover_image", user.getCoverImage())
                .claim("gender", user.getGender());
    }
}
}
