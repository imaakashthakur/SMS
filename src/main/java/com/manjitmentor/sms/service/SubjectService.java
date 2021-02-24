package com.manjitmentor.sms.service;

import com.manjitmentor.sms.dto.GenericResponse;

public interface SubjectService {
    GenericResponse findAllStudents();

    GenericResponse findStudentById();

    GenericResponse saveStudent();

    GenericResponse updateStudent();
}
