package base.application.data.db.base.repository.user;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphCrudRepository;

import base.application.data.db.base.model.user.entity.UserProfile;

public interface UserProfileRepository extends EntityGraphCrudRepository<UserProfile, Long> {
}