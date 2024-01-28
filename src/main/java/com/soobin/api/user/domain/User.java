package com.soobin.api.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id @GeneratedValue
    Long seq;
    String id;
    String password;
}
