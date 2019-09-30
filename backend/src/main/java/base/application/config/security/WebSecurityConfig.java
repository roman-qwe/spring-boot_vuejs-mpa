package base.application.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import base.application.config.security.jwt.JwtConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] ADMIN_ENDPOINT = { "/admin/**", "/api/admin/**" };
    private static final String[] USER_ENDPOINT = { "/user/**", "/api/user/**" };
    private static final String[] GUEST_ENDPOINT = { "/", "/guest/**", "/api/guest/**", "/login", "/about" };
    private static final String[] RESOURCE_ENDPOINT = { "/assets/**" };

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtConfigurerAdapter jwtConfigurerAdapter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(GUEST_ENDPOINT).permitAll(); // разрешены всем пользователям
        http.authorizeRequests().antMatchers(RESOURCE_ENDPOINT).permitAll(); // ресурсы
        http.authorizeRequests().antMatchers(USER_ENDPOINT).hasAnyAuthority("ADMIN", "USER");
        http.authorizeRequests().antMatchers(ADMIN_ENDPOINT).hasAuthority("ADMIN");

        // http.authorizeRequests().antMatchers("/api/login").permitAll();

        http.authorizeRequests().anyRequest().authenticated();
        http.apply(jwtConfigurerAdapter);
        // http.formLogin().loginProcessingUrl("/api/login").loginPage("/login").permitAll().and().logout().permitAll();

        // http.rememberMe();
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception
    // {
    // auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(PasswordUtil.B_CRYPT_PASSWORD_ENCODER)
    // .usersByUsernameQuery("select name username, password, active from user where
    // name=?")
    // .authoritiesByUsernameQuery("select name username, role roles from user where
    // name = ?");
    // }

}