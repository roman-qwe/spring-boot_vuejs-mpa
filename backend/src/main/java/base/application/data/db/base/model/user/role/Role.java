package base.application.data.db.base.model.user.role;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN(0), USER(1);

    private int value;

    Role(int value) {
        this.value = value;
    }

    public String getName() {
        return this.toString();
    }
}
