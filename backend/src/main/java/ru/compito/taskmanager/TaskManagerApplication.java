package ru.compito.taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.compito.taskmanager.config.CustomUserDetails;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.service.UserService;
import ru.compito.taskmanager.repository.UserRepository;


@SpringBootApplication
@EnableJpaAuditing
public class TaskManagerApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository userRepository, UserService userService) throws Exception {
		if (userRepository.count()==0)
			userService.saveUser(new User("admin","admin", "adminPassword","admin@gmail.com"));
		builder.userDetailsService(userDetailsService(userRepository)).passwordEncoder(passwordEncoder);
	}


	private UserDetailsService userDetailsService(final UserRepository userRepository) {
		return username -> new CustomUserDetails(userRepository.findByUsername(username));
	}
}
