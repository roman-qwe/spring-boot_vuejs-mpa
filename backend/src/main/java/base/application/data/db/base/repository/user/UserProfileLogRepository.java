package base.application.data.db.base.repository.user;

import org.springframework.data.repository.CrudRepository;

import base.application.data.db.base.model.user.log.UserProfileLog;

public interface UserProfileLogRepository extends CrudRepository<UserProfileLog, Long> {
}