package base.application.data.db.base.repository.user;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphCrudRepository;

import base.application.data.db.base.model.user.entity.User;

public interface UserRepository extends EntityGraphCrudRepository<User, Long> {
    Boolean existsByName(String name);

    User findByName(String name);

    User findByName(String name, EntityGraph entityGraph);
}