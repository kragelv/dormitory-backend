package by.bsuir.dorm.model.entity;

import by.bsuir.dorm.model.TokenPurpose;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "tbl_user_token",
        indexes = {
                @Index(
                        name = "idx_purpose",
                        columnList = "purpose"
                )
        }
)
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @Column(name = "token_hash",nullable = false, length = 60)
    private String tokenHash;

    @Column(name = "expiration_time", nullable = false)
    private Instant expirationTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose", nullable = false)
    private TokenPurpose purpose;
}