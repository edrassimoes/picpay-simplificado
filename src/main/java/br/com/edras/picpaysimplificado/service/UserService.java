package br.com.edras.picpaysimplificado.service;

import br.com.edras.picpaysimplificado.domain.User;
import br.com.edras.picpaysimplificado.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // READ
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // UPDATE
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    // DELETE
    public void deleteUserById(Long id) {
        userRepository.findById(id).orElse(null);
        userRepository.deleteById(id);
    }

}
