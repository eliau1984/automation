package com.algosec.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // שיטה לשמירת משתמש חדש
    public User save(User user) {
        return userRepository.save(user);
    }

    // שיטה לקבלת כל המשתמשים
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }
}
