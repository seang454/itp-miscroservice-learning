package kh.edu.istad.identity.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String permission;

    @ManyToMany(mappedBy = "permissions")
    private List<User> users;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;




}
