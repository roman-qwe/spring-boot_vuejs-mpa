package base.application.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import base.application.util.auth.PasswordUtil;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/index", "/registration", "/login", "/about", "/guest/**")
                .permitAll(); // разрешены // всем // пользователям
        http.authorizeRequests().antMatchers("/assets/**").permitAll(); // ресурсы
        http.authorizeRequests().antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER");
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");

        http.authorizeRequests().antMatchers("/api/login").permitAll();

        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin().loginProcessingUrl("/api/login").loginPage("/login").permitAll().and().logout().permitAll();

        http.rememberMe();
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(PasswordUtil.B_CRYPT_PASSWORD_ENCODER)
                .usersByUsernameQuery("select name username, password, active from user where name=?")
                .authoritiesByUsernameQuery("select name username, role roles from user where name = ?");
    }

}