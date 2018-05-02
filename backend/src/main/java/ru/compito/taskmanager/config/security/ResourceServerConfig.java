package ru.compito.taskmanager.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import ru.compito.taskmanager.config.ServiceConstants;

/**
 *Служба ресурсов, определяет уровень доступа к различным ресурсам
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(ServiceConstants.API_URL,ServiceConstants.REGISTER_PATH).permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/**","/api/tasks/**","/api/boards/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/boards/**").authenticated()
                .antMatchers(HttpMethod.PUT,"/api/attributes/**").authenticated()
                .antMatchers(HttpMethod.DELETE , "/post/**").hasAuthority("ROLE_ADMIN");
    }


}