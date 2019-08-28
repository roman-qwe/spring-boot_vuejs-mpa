package base.application.data.db.base.model.user.log;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import base.application.data.db.base.model.user.general.GUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AttributeOverride(name = "name", column = @Column(nullable = false, unique = false))
@Entity
@Table(name = "user_log")
public class UserLog extends GUser {

    @Column(name = "user_id", nullable = false, updatable = false)
    protected Long userId;

    public static UserLog from(GUser user) {
        return user == null ? null
                : UserLog.builder().userId(user.getId()).name(user.getName()).password(user.getPassword())
                        .active(user.getActive()).role(user.getRole()).build();
    }

    @Override
    public String toString() {
        return String.format("id: %s, userId: %s, name: %s, pass(nullOrEmpty?): %s, active: %s, role: %s.", id, userId,
                name, password == null || password.isEmpty(), active, role);
    }
}