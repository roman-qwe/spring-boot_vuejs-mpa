package base.application.config.security.jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import base.application.data.db.base.model.user.general.GUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class JwtUser implements UserDetails {

    private static final long serialVersionUID = 8562806319875826576L;

    private final Long id;
    private final String username;
    private final String password;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long id, String username, String firstName, String lastName, String email, String password,
            Collection<? extends GrantedAuthority> authorities, boolean enabled, Date lastPasswordResetDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    public static JwtUser from(GUser gUser) {
        JwtUser jUser = JwtUser.builder().id(gUser.getId()).username(gUser.getName()).password(gUser.getPassword())
                .enabled(gUser.getActive())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(gUser.getRole().name()))).build();
        return jUser;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

}