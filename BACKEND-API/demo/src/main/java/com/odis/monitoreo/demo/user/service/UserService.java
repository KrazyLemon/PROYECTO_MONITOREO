package com.odis.monitoreo.demo.user.service;

import com.odis.monitoreo.demo.auth.models.RegisterRequest;
import com.odis.monitoreo.demo.config.Security.SecurityUtils;
import com.odis.monitoreo.demo.user.models.User;
import com.odis.monitoreo.demo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    @Transactional
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public User getUserById(Integer id){
        return userRepository.findById(id).orElseThrow();
    }

    public Optional<User> getMe(String email){
      return userRepository.findByEmail(email);
    }

    @Transactional
    public User registerUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User updateUser(User user, Integer id){
        User usr = userRepository.findById(id).orElseThrow();
        usr.setFirstName(user.getFirstName());
        usr.setLastName(user.getLastName());
        usr.setEmail(user.getEmail());
        userRepository.save(usr);
        return usr;
    }

    @Transactional
    public boolean deleteUser(Integer id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
