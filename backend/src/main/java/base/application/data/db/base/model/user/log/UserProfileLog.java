package base.application.data.db.base.model.user.log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import base.application.data.db.base.model.user.general.GUserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "user_profile_log")
public class UserProfileLog extends GUserProfile {

    @Column(name = "profile_id", nullable = false, updatable = false)
    protected Long profileId;

    public static UserProfileLog from(GUserProfile profile) {
        return profile == null ? null
                : UserProfileLog.builder().user(profile.getUser()).profileId(profile.getId())
                        .dateChange(profile.getDateChange()).dateRegistration(profile.getDateRegistration())
                        .email(profile.getEmail()).build();
    }
}