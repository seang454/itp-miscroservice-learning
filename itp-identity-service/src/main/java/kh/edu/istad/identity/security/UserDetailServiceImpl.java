//package kh.edu.istad.identity.security;
//
//import kh.edu.istad.identity.domain.Role;
//import kh.edu.istad.identity.domain.User;
//import kh.edu.istad.identity.feature.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class UserDetailServiceImpl  implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User loggedUser = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(username));
//
////        String[] roles = loggedUser.getRoles().stream()
////                .map(
////                        Role::getRole
////                ).toArray(String[]::new);
//
////        Set<GrantedAuthority> authorities = loggedUser.getRoles().stream()
////                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
////                .collect(Collectors.toSet());
//
//        //importation for user authorities
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        loggedUser.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
//            role.getPermissions().forEach(permission -> {
//                authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
//            });
//        });
//        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
//                .username(loggedUser.getUsername())
//                .password(loggedUser.getPassword())
//                .authorities(authorities)
//                .build();
//
//
//        log.info("UserDetailsServiceImpl loadUserByUsername = {}", userDetails.getAuthorities());
//        log.info("UserDetails: {}", userDetails.getUsername());
//        return userDetails;
//    }
//}
