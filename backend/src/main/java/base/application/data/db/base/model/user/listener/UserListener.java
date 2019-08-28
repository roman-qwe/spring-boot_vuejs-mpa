package base.application.data.db.base.model.user.listener;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.application.data.db.base.model.user.entity.User;
import base.application.data.db.base.model.user.log.UserLog;
import base.application.data.db.base.repository.user.UserLogRepository;

@Component
public class UserListener {

    static private UserLogRepository userLogRepository;

    @Autowired
    public void init(UserLogRepository userLogRepository) {
        UserListener.userLogRepository = userLogRepository;
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    public void changed(User user) {
        UserLog userLog = UserLog.from(user);
        if (userLog == null)
            return;
        userLogRepository.save(userLog);
    }

}