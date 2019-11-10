package base.application.data.db.base.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import base.application.data.db.base.model.user.entity.User;
import base.application.data.db.base.model.user.entity.UserProfile;
import base.application.data.db.base.repository.user.UserProfileRepository;
import base.application.data.db.base.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public boolean existsByName(String name) {
        return userRepository.existsByName(name);
    }

    public User findByName(String name) {
        log.info("IN findByName without profile");
        return userRepository.findByName(name);
    }

    @Transactional
    public User findByNameWithProfile(String name) {
        log.info("IN findByNameWithProfile with profile");

        log.info("IN findByNameWithProfile select user");
        User user = userRepository.findByName(name);
        log.info("IN findByNameWithProfile select profile");
        UserProfile profile = userProfileRepository.findProfileByUser(user);

        user.setProfile(profile);
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}