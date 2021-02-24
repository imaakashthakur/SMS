package com.manjitmentor.sms.repository;

import com.manjitmentor.sms.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
