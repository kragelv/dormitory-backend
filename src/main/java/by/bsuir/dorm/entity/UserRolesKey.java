package by.bsuir.dorm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class UserRolesKey implements Serializable {
    private Long userId;

    @Enumerated(EnumType.ORDINAL)
    private Role role;
}
