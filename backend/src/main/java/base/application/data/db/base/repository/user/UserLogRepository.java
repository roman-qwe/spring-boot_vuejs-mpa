package base.application.data.db.base.repository.user;

import org.springframework.data.repository.CrudRepository;

import base.application.data.db.base.model.user.log.UserLog;

public interface UserLogRepository extends CrudRepository<UserLog, Long> {
    Boolean existsByName(String name);

    UserLog findByName(String name);

}