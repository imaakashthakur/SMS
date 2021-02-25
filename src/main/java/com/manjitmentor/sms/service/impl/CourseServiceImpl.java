package com.manjitmentor.sms.service.impl;

import com.manjitmentor.sms.builder.ResponseBuilder;
import com.manjitmentor.sms.constant.MessagesConstants;
import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.model.ApplicationUser;
import com.manjitmentor.sms.model.Course;
import com.manjitmentor.sms.repository.CourseRepository;
import com.manjitmentor.sms.request.SaveCourseRequest;
import com.manjitmentor.sms.request.UpdateCourseRequest;
import com.manjitmentor.sms.response.dto.CourseDTO;
import com.manjitmentor.sms.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public GenericResponse findAllCourses() {
        final List<Course> courses = courseRepository.findAll();
        if(courses.isEmpty()){
            return ResponseBuilder.buildFailure(MessagesConstants.COURSE_NOT_FOUND);
        }
        List<CourseDTO> courseDTOList = new ArrayList<>();

        courseDTOList = courses
                .stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());

        return ResponseBuilder.buildSuccess(MessagesConstants.COURSE_FOUND, courseDTOList);

    }

    @Override
    public GenericResponse findCourseById(Long id) {
        final Optional<Course> courseOptional = courseRepository.findById(id);
        if(!courseOptional.isPresent()){
            return ResponseBuilder.buildFailure(MessagesConstants.COURSE_NOT_FOUND);
        }
        if(courseOptional.get().getIsActive() == 'N'){
            return ResponseBuilder.buildFailure(MessagesConstants.COURSE_WAS_DELETED);
        }
        CourseDTO response = modelMapper.map(courseOptional.get(), CourseDTO.class);
        log.info("Response: {}", response);
        return ResponseBuilder.buildSuccess(MessagesConstants.COURSE_FOUND, response);
    }

    @Override
    public GenericResponse saveCourse(SaveCourseRequest request) {
        Course course = modelMapper.map(request, Course.class);
        course.setCreatedBy(new ApplicationUser(1L));
        course.setIsActive('Y');
        log.info("course: {}", course);
        log.info(course.getCreatedBy().toString());
        courseRepository.save(course);
        return ResponseBuilder.buildSuccess(MessagesConstants.COURSE_SAVED);
    }

    @Override
    public GenericResponse updateCourse(UpdateCourseRequest request, Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if(!courseOptional.isPresent()){
            return ResponseBuilder.buildFailure(MessagesConstants.COURSE_NOT_FOUND);
        }
        Course course = new Course();
        course = modelMapper.map(request, Course.class);
        course.setId(id);
        course.setCreatedBy(new ApplicationUser(1L));
        log.info("course: {}", course);
        courseRepository.save(course);
        return ResponseBuilder.buildSuccess(MessagesConstants.COURSE_UPDATED);

    }

    @Override
    public GenericResponse deleteCourse(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if(!courseOptional.isPresent()){
            return ResponseBuilder.buildFailure(MessagesConstants.COURSE_NOT_FOUND);
        }
        else{
            Course course = new Course();
            course = modelMapper.map(courseOptional.get(), Course.class);
            course.setIsActive('N');
            courseRepository.save(course);
            return ResponseBuilder.buildSuccess(MessagesConstants.COURSE_WAS_DELETED);
        }
    }

    @Override
    public GenericResponse findDeletedCourses() {
        final List<Course> courseOptional = courseRepository.findAll();
        if(courseOptional.isEmpty()){
            ResponseBuilder.buildFailure(MessagesConstants.COURSE_NOT_FOUND);
        }
        List<CourseDTO> courseDTOList = new ArrayList<>();
        courseDTOList = courseOptional.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
        List<CourseDTO> courseTrash = new ArrayList<>();
        for(CourseDTO c : courseDTOList){
            if(c.getIsActive() == 'N'){
                courseTrash.add(c);
            }
        }
        return ResponseBuilder.buildSuccess(MessagesConstants.COURSE_FOUND, courseTrash);
    }
}