package ru.compito.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.repository.UserRepository;
import ru.compito.taskmanager.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service(value = "UserService")
@Transactional
public class UserServiceImpl implements UserService,UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public List<User> findByTaskId(Integer taskId) {
        return userRepository.findByTaskId(taskId);
    }
    @Override
    public User getUserById(Integer Id) {
        return userRepository.findOne(Id);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);

    }

    @Override
    public void deleteUserById(Integer Id) {
        userRepository.delete(Id);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
