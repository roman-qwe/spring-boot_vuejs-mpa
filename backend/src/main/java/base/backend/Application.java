package base.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.default", "dev");
		SpringApplication.run(Application.class, args);
	}

}
