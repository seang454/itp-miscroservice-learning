package kh.edu.istad.identity.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class CustomUserDetails extends User {

    private String uuid;
    private String email;
    private String familyName;
    private String givenName;
    private String phoneNumber;
    private String profileImage;
    private String gender;
    private String coverImage;


    public CustomUserDetails(String username,
                             String password,
                             boolean enabled,
                             boolean accountNonExpired,
                             boolean credentialsNonExpired,
                             boolean accountNonLocked,
                             Collection<? extends GrantedAuthority> authorities,
                             String uuid,
                             String email,
                             String familyName,
                             String givenName,
                             String phoneNumber,
                             String profileImage,
                             String coverImage,
                             String gender) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.uuid = uuid;
        this.email = email;
        this.familyName = familyName;
        this.givenName = givenName;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.gender = gender;
        this.coverImage = coverImage;
    }
}