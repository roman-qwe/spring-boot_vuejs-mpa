package base.application.data.db.base.model.user.general;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import base.application.data.db.base.model.user.role.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class GUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    protected Long id;

    @Column(nullable = false, unique = true)
    protected String name;

    @Column(nullable = false)
    protected String password;

    @Column(nullable = false)
    @Builder.Default
    protected Boolean active = true;

    @Column(nullable = false)
    @Builder.Default
    protected Role role = Role.USER;

    @Override
    public String toString() {
        return name;
    }
}