package by.bsuir.dorm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_t")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "card_id", nullable = false, unique = true, length = 64)
    private String cardId;

    @Column(name = "password_hash", nullable = false, length = 60)
    private String passwordHash;

    @Column(name = "password_need_reset")
    private Boolean passwordNeedReset;

    @Column(name = "email", unique = true, length = 128)
    private String email;

    @Column(name = "email_confirmed")
    private Boolean emailConfirmed;

    @Embedded
    private FullName fullName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRoles> roleOrdinals = new LinkedHashSet<>();


    //TODO: phone

    //TODO: address


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleOrdinals
                .stream()
                .map(r -> new SimpleGrantedAuthority())
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}