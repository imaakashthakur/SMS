package com.manjitmentor.sms.repository;

import com.manjitmentor.sms.model.ApplicationUser;
import com.manjitmentor.sms.request.SaveUserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
}
