package base.application.data.db.base.repository.user;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphCrudRepository;
import base.application.data.db.base.entity.user.User;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;

public interface UserRepository extends EntityGraphCrudRepository<User, Long> {
    Boolean existsByName(String name);

    User findByName(String name, EntityGraph entityGraph);
}