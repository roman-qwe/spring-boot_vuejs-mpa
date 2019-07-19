package base.application.load.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProdLoad implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevLoad.class);

    @Autowired
    UsersLoad usersLoad;

    public void run(ApplicationArguments args) {
        LOGGER.info("+++++ PROD");
    }
}