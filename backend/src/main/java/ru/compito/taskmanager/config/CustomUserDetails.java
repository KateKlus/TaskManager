package ru.compito.taskmanager.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.compito.taskmanager.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Реализует интерфейс UserDetails, предоставляет необходимую информацию для
 * построения объекта Authentication из Repository объектов
 */
public class CustomUserDetails implements UserDetails {
/*
    @Autowired
    private RoleService roleService; */

    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;

    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        //List<Role> roles = roleService.findAll();
        this.authorities = translate();
    }

    private Collection<? extends GrantedAuthority> translate() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        /*for (Role role : roles) {
            String name = role.getRoleName().toUpperCase();
            //Удостоверимся что все роли начинаются с "ROLE_"
            if (!name.startsWith("ROLE_"))
                name = "ROLE_" + name;
            authorities.add(new SimpleGrantedAuthority(name));
        }*/
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));

        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}