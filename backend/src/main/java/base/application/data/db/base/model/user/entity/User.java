package base.application.data.db.base.model.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import base.application.data.db.base.model.user.general.GUser;
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
// @EntityListeners({ UserListener.class })
public class User extends GUser {

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = UserProfile.class)
    // @Fetch(FetchMode.JOIN)
    // @PrimaryKeyJoinColumn(name = "user_id")
    // @LazyToOne(value = LazyToOneOption.NO_PROXY)
    protected UserProfile profile;

}