package base.application.data.db.base.model.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = UserProfile.class)
    @PrimaryKeyJoinColumn
    protected UserProfile profile;

}