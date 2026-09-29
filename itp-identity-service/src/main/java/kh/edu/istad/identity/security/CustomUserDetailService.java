package kh.edu.istad.identity.security;


import kh.edu.istad.identity.domain.User;
import kh.edu.istad.identity.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userLogged = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        Set<GrantedAuthority> authorities = new HashSet<>();
        userLogged.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
            role.getPermissions().forEach(permission -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + permission.getPermission()));
            });
        });

        CustomUserDetails customUserDetails = new CustomUserDetails(
                userLogged.getUsername(),
                userLogged.getPassword(),
                userLogged.getIsEnabled(),
                userLogged.getAccountNonExpired(),
                userLogged.getCredentialsNonExpired(),
                userLogged.getAccountNonLocked(),
                authorities,
                userLogged.getUuid(),
                userLogged.getEmail(),
                userLogged.getFamilyName(),
                userLogged.getGivenName(),
                userLogged.getPhoneNumber(),
                userLogged.getProfileImage(),
                userLogged.getCoverImage(),
                userLogged.getGender()
        );

        log.info("CustomUserDetails loaded for user: {}", customUserDetails.getUsername());
        log.info("User authorities: {}", customUserDetails.getAuthorities());

        return customUserDetails;
    }
}
