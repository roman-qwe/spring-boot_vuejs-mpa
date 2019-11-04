package base.application.config.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.sql.DataSource;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.ObjectArrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import base.application.config.security.jwt.JwtConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] ADMIN_ENDPOINT = { "/admin/**", "/api/admin/**" };
    private static final String[] USER_ENDPOINT = { "/user/**", "/api/user/**" };
    private static final String[] GUEST_ENDPOINT = { "/", "/guest/**", "/api/guest/**", "/login", "/about" };
    private static final String[] RESOURCE_ENDPOINT = { "/assets/**" };

    public static final String[] EXCLUDE_FROM_JWT_FILTER = ObjectArrays.concat(GUEST_ENDPOINT, RESOURCE_ENDPOINT, String.class);

    @Qualifier("dataSource")
    private DataSource dataSource;

    private JwtConfigurerAdapter jwtConfigurerAdapter;

    @Autowired
    public WebSecurityConfig(DataSource dataSource, JwtConfigurerAdapter jwtConfigurerAdapter) {
        this.dataSource = dataSource;
        this.jwtConfigurerAdapter = jwtConfigurerAdapter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(GUEST_ENDPOINT).permitAll(); // разрешены всем пользователям
        http.authorizeRequests().antMatchers(RESOURCE_ENDPOINT).permitAll(); // ресурсы
        http.authorizeRequests().antMatchers(USER_ENDPOINT).hasAnyAuthority("ADMIN", "USER");
        http.authorizeRequests().antMatchers(ADMIN_ENDPOINT).hasAuthority("ADMIN");

        http.apply(jwtConfigurerAdapter);

        // http.rememberMe();
    }
}