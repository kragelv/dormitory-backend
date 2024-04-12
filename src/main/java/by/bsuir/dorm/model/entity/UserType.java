package by.bsuir.dorm.model.entity;

import by.bsuir.dorm.model.listener.UserTypeListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.SortNatural;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_user_type")
@NaturalIdCache
@EntityListeners(UserTypeListener.class)
public class UserType implements Comparable<UserType> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NaturalId
    @Column(name = "name", nullable = false, unique = true, length = 64)
    private String name;

    @ManyToMany(
            mappedBy = "compatibleUserTypes",
            cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}
    )
    @SortNatural
    @Setter(AccessLevel.NONE)
    private SortedSet<Role> roles = new TreeSet<>();

    public void addRole(Role role) {
        roles.add(role);
        role.getCompatibleUserTypes().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getCompatibleUserTypes().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserType)) {
            return false;
        }
        UserType other = (UserType) o;
        return Objects.equals(name, other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(UserType o) {
        return id.compareTo(o.getId());
    }
}