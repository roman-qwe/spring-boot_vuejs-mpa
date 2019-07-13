package base.backend.repository.base;

import org.springframework.data.repository.CrudRepository;

import base.backend.model.entity.base.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Boolean existsByName(String name);

    User findUserByName(String name);

}