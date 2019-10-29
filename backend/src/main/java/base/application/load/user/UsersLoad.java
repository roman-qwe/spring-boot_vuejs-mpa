package base.application.load.user;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.application.data.db.base.model.user.entity.User;
import base.application.data.db.base.model.user.entity.UserProfile;
import base.application.data.db.base.model.user.role.Role;
import base.application.data.db.base.repository.user.UserProfileRepository;
import base.application.data.db.base.repository.user.UserRepository;
import base.application.util.auth.PasswordUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UsersLoad {

    UserRepository userRepository;
    UserProfileRepository userProfileRepository;

    @Autowired
    public UsersLoad(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public void Run() {
        if (!userRepository.existsByName("admin")) {
            User admin = User.builder().name("admin")
                    .password(PasswordUtil.B_CRYPT_PASSWORD_ENCODER.encode("admin_password")).role(Role.ADMIN).build();
            System.out.println("admin builded");
            userRepository.save(admin);
            System.out.println("admin saved");
        }
        if (!userRepository.existsByName("user")) {
            User user = User.builder().name("user")
                    .password(PasswordUtil.B_CRYPT_PASSWORD_ENCODER.encode("user_password")).role(Role.USER).build();
            user.setProfile(UserProfile.builder().email("user@email.com").build());
            user.getProfile().setUser(user);
            System.out.println("user builded");
            userRepository.save(user);
            System.out.println("admin saved");
        }

        log.info("IN Run user with user.profile: {}", userRepository.findByName("user", EntityGraphs.named("User.profile")).getProfile().getEmail());
        log.info("IN Run simple user: {}", userRepository.findByName("user"));

        userProfileRepository.findAll().forEach(System.out::println);
    }
}