package base.application.data.db.base.model.user.log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import base.application.data.db.base.model.user.entity.UserProfile;
import base.application.data.db.base.model.user.general.GUserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "user_profile_log")
public class UserProfileLog extends GUserProfile {

    @Column(name = "profile_id", nullable = false, updatable = false)
    protected Long profileId;

    public static UserProfileLog from(UserProfile profile) {
        return profile == null ? null
                : UserProfileLog.builder().profileId(profile.getId())
                        .dateChange(profile.getDateChange()).dateRegistration(profile.getDateRegistration())
                        .email(profile.getEmail()).build();
    }
}