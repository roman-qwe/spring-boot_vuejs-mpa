package base.application.load.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.application.data.db.base.model.user.entity.User;
import base.application.data.db.base.model.user.entity.UserProfile;
import base.application.data.db.base.model.user.role.Role;
import base.application.data.db.base.service.UserService;
import base.application.util.auth.PasswordUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UsersLoad {

    UserService userService;

    @Autowired
    public UsersLoad(UserService userService) {
        this.userService = userService;
    }

    public void Run() {
        if (!userService.existsByName("admin")) {
            User admin = User.builder().name("admin")
                    .password(PasswordUtil.B_CRYPT_PASSWORD_ENCODER.encode("admin_password")).role(Role.ADMIN).build();
            System.out.println("admin builded");
            userService.save(admin);
            System.out.println("admin saved");
        }
        if (!userService.existsByName("user")) {
            User user = User.builder().name("user")
                    .password(PasswordUtil.B_CRYPT_PASSWORD_ENCODER.encode("user_password")).role(Role.USER).build();
            user.setProfile(UserProfile.builder().email("user@email.com").build());
            // user.getProfile().setUser(user);
            System.out.println("user builded");
            userService.save(user);
            System.out.println("user saved");
        }

        log.info("IN Run simple user: {}", userService.findByName("user"));
        log.info("IN Run user with user.profile: {}", userService.findByNameWithProfile("user").getProfile());
    }
}