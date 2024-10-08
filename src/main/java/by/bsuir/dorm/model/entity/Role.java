package by.bsuir.dorm.model.entity;

import by.bsuir.dorm.model.listener.RoleListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.SortNatural;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_role")
@NaturalIdCache
@EntityListeners(RoleListener.class)
public class Role implements Comparable<Role> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NaturalId
    @Column(name = "name", nullable = false, unique = true, length = 64)
    private String name;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "m2m_role_user_type",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_type_id"))
    @SortNatural
    private SortedSet<UserType> compatibleUserTypes = new TreeSet<>();

    @ManyToMany(
            mappedBy = "roles",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    private List<User> users = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        Role other = (Role) o;
        return Objects.equals(name, other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Role o) {
        return id.compareTo(o.getId());
    }
}