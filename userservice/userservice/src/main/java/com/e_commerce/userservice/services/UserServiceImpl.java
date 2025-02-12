package com.e_commerce.userservice.services;

import com.e_commerce.userservice.models.Sessions;
import com.e_commerce.userservice.models.User;
import com.e_commerce.userservice.repositories.SessionsRepository;
import com.e_commerce.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("userserviceimpl")
public class UserServiceImpl implements UserService {

    public UserRepository userRepository;
    public SessionsRepository sessionsRepository;

    public UserServiceImpl(UserRepository userRepository,
                           SessionsRepository sessionsRepository) {
        this.userRepository = userRepository;
        this.sessionsRepository = sessionsRepository;
    }
    public String createUser(User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        userRepository.save(newUser);

        return "User with email " + user.getEmail() + " created successfully";
    }
    public String loginUser(User user) {
        User newUser = userRepository.findByEmail(user.getEmail());
        if (newUser == null) {
            return "User with email " + user.getEmail() + " not found";
        }
        else if (!user.getPassword().equals(newUser.getPassword())) {
            return "FAIL";
        }
        else {
            List<Sessions> userSessions = sessionsRepository.findAllByUserEmail(user.getEmail());
            if (userSessions.size() >= 2){
                return "You have exceeded 2 sessions limit";
            }

            Sessions session = new Sessions();
            session.setUser(newUser);
            session.setToken(UUID.randomUUID().toString());
            sessionsRepository.save(session);

            return "OK";
        }
    }
    @Transactional
    public String deleteSession(String token) {
        sessionsRepository.deleteByToken(token);
        return "Session with token " + token + " deleted successfully";
    }
}
