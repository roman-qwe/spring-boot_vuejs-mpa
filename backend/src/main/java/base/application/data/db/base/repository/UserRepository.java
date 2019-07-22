package base.application.data.db.base.repository;

import org.springframework.data.repository.CrudRepository;

import base.application.data.db.base.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Boolean existsByName(String name);

    User findUserByName(String name);

}