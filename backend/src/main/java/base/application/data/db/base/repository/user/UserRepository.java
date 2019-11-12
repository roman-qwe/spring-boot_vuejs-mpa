package base.application.data.db.base.repository.user;

import org.springframework.data.repository.CrudRepository;

import base.application.data.db.base.model.user.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Boolean existsByName(String name);

    User findByName(String name);

}