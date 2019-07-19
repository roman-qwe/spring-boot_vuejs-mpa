package base.application.load.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.application.model.entity.base.user.User;
import base.application.model.entity.base.user.role.Role;
import base.application.repository.base.UserRepository;
import base.application.util.auth.PasswordUtil;

@Component
public class UsersLoad {

    @Autowired
    UserRepository userRepository;

    public void Run() {
        if (!userRepository.existsByName("admin")) {
            User admin = User.builder().name("admin")
                    .password(PasswordUtil.B_CRYPT_PASSWORD_ENCODER.encode("admin_password")).role(Role.ADMIN).build();
            userRepository.save(admin);
        }
        if (!userRepository.existsByName("user")) {
            User user = User.builder().name("user")
                    .password(PasswordUtil.B_CRYPT_PASSWORD_ENCODER.encode("user_password")).role(Role.USER).build();
            userRepository.save(user);
        }
    }
}