package com.manjitmentor.sms.repository;

import com.manjitmentor.sms.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByEmailAddress(String emailAddress);
}
