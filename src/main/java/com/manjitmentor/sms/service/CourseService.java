package com.manjitmentor.sms.service;

import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.request.SaveCourseRequest;
import com.manjitmentor.sms.request.UpdateCourseRequest;

public interface CourseService {
    GenericResponse findActiveCourses();

    GenericResponse findAllCourses();

    GenericResponse findCourseById(Long id);

    GenericResponse saveCourse(SaveCourseRequest request);

    GenericResponse updateCourse(UpdateCourseRequest request, Long id);

    GenericResponse deleteCourse(Long id);

    GenericResponse findDeletedCourses();

    GenericResponse rollBackDeletedCourse(Long id);

    GenericResponse rollBackAllDeletedCourses();


}
