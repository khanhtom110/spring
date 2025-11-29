package vn.hoidanit.laptopshop.service;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handleHello() {
        return "Hello from Service";
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeErrorException(null));
    }

    public void updateUser(User newUser) {
        User user = getUser(newUser.getId());
        System.out.println(user);
        user.setAddress(newUser.getAddress());
        user.setEmail(newUser.getEmail());
        user.setFullName(newUser.getFullName());
        user.setPhone(newUser.getPhone());
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
