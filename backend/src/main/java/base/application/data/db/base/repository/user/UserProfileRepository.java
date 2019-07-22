package base.application.data.db.base.repository.user;

import org.springframework.data.repository.CrudRepository;

import base.application.data.db.base.entity.user.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
}