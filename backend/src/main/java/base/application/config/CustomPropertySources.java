package base.application.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class CustomPropertySources {

    private final static List<String> PROPERTIES = List.of("application.properties", "hibernate.properties",
            "logger.properties");

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = CustomPropertySources.PROPERTIES.stream().map(item -> new ClassPathResource(item))
                .toArray(Resource[]::new);

        pspc.setLocations(resources);
        pspc.setIgnoreUnresolvablePlaceholders(true);
        return pspc;
    }

}