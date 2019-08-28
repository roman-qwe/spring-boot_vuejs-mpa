package base.application.data.db.base.model.user.listener;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.application.data.db.base.model.user.entity.UserProfile;
import base.application.data.db.base.model.user.log.UserProfileLog;
import base.application.data.db.base.repository.user.UserProfileLogRepository;

@Component
public class UserProfileListener {

    static private UserProfileLogRepository userProfileLogRepository;

    @Autowired
    public void init(UserProfileLogRepository userProfileLogRepository) {
        UserProfileListener.userProfileLogRepository = userProfileLogRepository;
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    public void changed(UserProfile profile) {
        UserProfileLog profileLog = UserProfileLog.from(profile);
        if (profileLog == null)
            return;
        userProfileLogRepository.save(profileLog);
    }

}