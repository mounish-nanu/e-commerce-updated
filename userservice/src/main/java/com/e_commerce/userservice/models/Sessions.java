package com.e_commerce.userservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.bind.support.SessionStatus;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Sessions{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User user;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.ORDINAL)
    private SessionsStatus sessionStatus;
}
