package base.application.data.db.base.repository.user;

import org.springframework.data.repository.CrudRepository;

import base.application.data.db.base.model.user.entity.User;
import base.application.data.db.base.model.user.entity.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {

    UserProfile findProfileByUser(User user);

}