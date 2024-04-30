package by.bsuir.dorm.model.entity;

import by.bsuir.dorm.model.listener.UserListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_user")
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(UserListener.class)
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "card_id", nullable = false, unique = true, length = 64)
    private String cardId;

    @Column(name = "phone_number", nullable = false, length = 16)
    private String phoneNumber;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "password_need_reset")
    private Boolean passwordNeedReset;

    @Column(name = "email", length = 128)
    private String email;

    @Column(name = "email_confirmed")
    private Boolean emailConfirmed;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "surname", column = @Column(name = "surname", length = 40, nullable = false)),
            @AttributeOverride(name = "name", column = @Column(name = "name", length = 40, nullable = false))
    })
    private FullName fullName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "m2m_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new LinkedHashSet<>();

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    public abstract String getTypename();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final Stream<String> streamType = Stream.of(getTypename());
        final Stream<String> resultStream;
        if (roles != null) {
            final Stream<String> streamRoles = roles
                    .stream()
                    .map(Role::getName);
            resultStream = Stream.concat(streamType, streamRoles);
        } else {
            resultStream = streamType;
        }
        return resultStream
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User other = (User) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}