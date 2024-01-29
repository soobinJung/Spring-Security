package com.soobin.api.user.repository;

import com.soobin.api.user.domain.SoobinUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoobinUserRepository extends JpaRepository<SoobinUser, Long> {

    SoobinUser findByEmail(String email);
}
