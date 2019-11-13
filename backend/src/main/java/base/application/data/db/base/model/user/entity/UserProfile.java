package base.application.data.db.base.model.user.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import base.application.data.db.base.model.user.general.GUserProfile;
import base.application.data.db.base.model.user.listener.UserProfileListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "user_profile")
@EntityListeners({ UserProfileListener.class })
public class UserProfile extends GUserProfile {

    @OneToOne(optional = true, fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    protected User user;

}