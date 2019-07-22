package base.application.data.db.base.entity.user.role;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN(0), USER(1);

    private int value;

    Role(int value) {
        this.value = value;
    }
}
