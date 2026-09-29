package co.istad.ebanking.controller;

import co.istad.ebanking.dto.AuthenticationResponse;
import co.istad.ebanking.dto.ProfileResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
//    @GetMapping("/oauth2/authenticated/me")
//    public Mono<OAuth2User> me(@AuthenticationPrincipal OAuth2User user) {
//        return Mono.just(user);
//    }
@GetMapping("/me")
public Mono<ProfileResponse> me(@AuthenticationPrincipal OidcUser principal) {

    return Mono.just(ProfileResponse.builder()
            .uuid(principal.getAttribute("uuid"))
                    .username(principal.getPreferredUsername() != null ? principal.getPreferredUsername() : principal.getName())
            .email(principal.getEmail())
            .givenName(principal.getGivenName())
            .familyName(principal.getFamilyName())
                    .dob(principal.getAttribute("dob"))
                    .gender(principal.getAttribute("gender"))
                    .profileImage(principal.getAttribute("profile_image"))
                    .coverImage(principal.getAttribute("cover_image"))
                    .phoneNumber(principal.getAttribute("phone_number"))
                    .dob(principal.getAttribute("dob"))
            .build());
}

    @GetMapping("/is-authenticated")
    public AuthenticationResponse isAuthenticated(Authentication authentication) {
        return AuthenticationResponse.builder()
                .isAuthenticated(authentication != null)
                .build();
    }

}
