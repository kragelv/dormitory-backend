package by.bsuir.dorm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_t")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "card_id", nullable = false, unique = true, length = 64)
    private String cardId;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "password_need_reset")
    private Boolean passwordNeedReset;

    @Column(name = "email", length = 128) //TODO: implement unique with nulls
    private String email;

    @Column(name = "email_confirmed")
    private Boolean emailConfirmed;

    @Embedded
    private FullName fullName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    //TODO: phone

    //TODO: address

    @Override
    public abstract Collection<? extends GrantedAuthority> getAuthorities();

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}