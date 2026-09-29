package kh.edu.istad.identity.config.jpa;

import kh.edu.istad.identity.security.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class EntityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("itp");
    }

//    @Override
//    public Optional<String> getCurrentAuditor() {
//
//        Authentication authentication =
//                SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null ||
//                !authentication.isAuthenticated() ||
//                authentication instanceof AnonymousAuthenticationToken) {
//            return Optional.empty();
//        }
//
//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof CustomUserDetails userDetails) {
//            // ✅ SAFE: store UUID or username, NOT the object
//            return Optional.of(userDetails.getUuid());
//        }
//
//        if (principal instanceof String s) {
//            return Optional.of(s);
//        }
//
//        return Optional.empty();
//    }

}
