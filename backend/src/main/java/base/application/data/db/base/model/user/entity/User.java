package base.application.data.db.base.model.user.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import base.application.data.db.base.model.user.general.GUser;
import base.application.data.db.base.model.user.listener.UserListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "user")
@EntityListeners({ UserListener.class })
public class User extends GUser {

    @Transient
    protected UserProfile profile;

}