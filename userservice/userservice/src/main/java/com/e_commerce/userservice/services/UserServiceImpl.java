package com.e_commerce.userservice.services;

import com.e_commerce.userservice.Exceptions.UserAlreadyExistsException;
import com.e_commerce.userservice.Exceptions.UserNotFoundException;
import com.e_commerce.userservice.Exceptions.WrongPasswordException;
import com.e_commerce.userservice.dtos.UserDto;
import com.e_commerce.userservice.models.Sessions;
import com.e_commerce.userservice.models.User;
import com.e_commerce.userservice.repositories.SessionsRepository;
import com.e_commerce.userservice.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("userserviceimpl")
public class UserServiceImpl implements UserService {

    public UserRepository userRepository;
    public SessionsRepository sessionsRepository;
    public BCryptPasswordEncoder bCryptPasswordEncoder;
//    private SecretKey key = Jwts.SIG.HS256.key().build();
    private static final String SECRET = "your-very-long-secret-key-which-has-32-bytes!";

    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));


    public UserServiceImpl(UserRepository userRepository,
                           SessionsRepository sessionsRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionsRepository = sessionsRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public String createUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("User with email already exists");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);

        return "User with email " + user.getEmail() + " created successfully";
    }
    public String loginUser(User user) throws UserNotFoundException {
        User newUser = userRepository.findByEmail(user.getEmail());
        if (newUser == null) {
             throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
        }
        boolean matches = bCryptPasswordEncoder.matches(user.getPassword(), newUser.getPassword());
        if (matches) {
            String token =  createJwtToken(newUser.getId(), newUser.getEmail());
            Sessions session = new Sessions();
            session.setUser(newUser);
            session.setToken(token);
            sessionsRepository.save(session);
            return token;
        }
        else {
//            List<Sessions> userSessions = sessionsRepository.findAllByUserEmail(user.getEmail());
//            if (userSessions.size() >= 2){
//                return "You have exceeded 2 sessions limit";
//            }
//
//            Sessions session = new Sessions();
//            session.setUser(newUser);
//            session.setToken(UUID.randomUUID().toString());
//            sessionsRepository.save(session);

            throw new WrongPasswordException("Wrong password");
        }
    }
    private String createJwtToken(Long userId, String email) {
        Map<String, Object> jwtToken = new HashMap<>();
        jwtToken.put("id", userId);
        jwtToken.put("email", email);

        String token = Jwts.builder().claims(jwtToken).signWith(key).compact();

        return token;
    }
    @Transactional
    public String deleteSession(String token) {
        sessionsRepository.deleteByToken(token);
        return "Session with token " + token + " deleted successfully";
    }

    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::from)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }
}
