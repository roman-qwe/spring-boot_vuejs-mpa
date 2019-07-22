package base.application.load.user;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import base.application.data.db.base.entity.user.User;
import base.application.data.db.base.entity.user.UserProfile;
import base.application.data.db.base.entity.user.role.Role;
import base.application.data.db.base.repository.user.UserRepository;
import base.application.util.auth.PasswordUtil;

@Component
public class UsersLoad {

    @Autowired
    UserRepository userRepository;

    public void Run() {
        System.out
                .println(userRepository.findByName("user", EntityGraphs.named("User.profile")).getProfile().getEmail());

        if (!userRepository.existsByName("admin")) {
            User admin = User.builder().name("admin")
                    .password(PasswordUtil.B_CRYPT_PASSWORD_ENCODER.encode("admin_password")).role(Role.ADMIN).build();
            userRepository.save(admin);
        }
        if (!userRepository.existsByName("user")) {
            User user = User.builder().name("user")
                    .password(PasswordUtil.B_CRYPT_PASSWORD_ENCODER.encode("user_password")).role(Role.USER).build();
            user.setProfile(UserProfile.builder().email("user@email.com").build());
            user.getProfile().setUser(user);
            userRepository.save(user);
        }
    }
}