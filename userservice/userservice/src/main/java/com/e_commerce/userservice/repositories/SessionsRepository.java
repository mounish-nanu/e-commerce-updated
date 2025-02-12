package com.e_commerce.userservice.repositories;

import com.e_commerce.userservice.models.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessionsRepository extends JpaRepository<Sessions, Long> {
    @Query("SELECT s FROM Sessions s WHERE s.user.email = :email")
    List<Sessions> findAllByUserEmail(String email);
    void deleteByToken(String token);
}
