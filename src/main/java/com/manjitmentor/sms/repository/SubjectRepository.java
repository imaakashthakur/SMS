package com.manjitmentor.sms.repository;

import com.manjitmentor.sms.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
