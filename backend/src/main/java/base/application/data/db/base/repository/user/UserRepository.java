package base.application.data.db.base.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import base.application.data.db.base.model.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByName(String name);

    User findByName(String name);

}