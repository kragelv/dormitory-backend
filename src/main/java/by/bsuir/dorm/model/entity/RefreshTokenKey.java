package by.bsuir.dorm.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class RefreshTokenKey implements Serializable {
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH},
            optional = false)
    private User user;

    @Column(name = "session_id", nullable = false)
    private UUID sessionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefreshTokenKey)) {
            return false;
        }
        RefreshTokenKey other = (RefreshTokenKey) o;
        return Objects.equals(user.getId(), other.getUser().getId())
                && Objects.equals(other.getSessionId(), sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getId(), sessionId);
    }
}