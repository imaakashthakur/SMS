package com.manjitmentor.sms.service.impl;

import com.manjitmentor.sms.builder.ResponseBuilder;
import com.manjitmentor.sms.constant.MessagesConstants;
import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.model.Subject;
import com.manjitmentor.sms.repository.SubjectRepository;
import com.manjitmentor.sms.response.dto.SubjectDTO;
import com.manjitmentor.sms.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GenericResponse findAllStudents() {
        final List<Subject> subjectList = subjectRepository.findAll();
        if(!subjectList.isEmpty()){
            return ResponseBuilder.buildFailure(MessagesConstants.SUBJECT_NOT_FOUND);
        }
        List<SubjectDTO> response = new ArrayList<>();
        response = subjectList
                .stream()
                .map(subject -> modelMapper.map(subject, SubjectDTO.class)).collect(Collectors.toList());
        log.info("subjects: {}", response);
        return ResponseBuilder.buildSuccess(MessagesConstants.COURSE_FOUND, response);
    }

    @Override
    public GenericResponse findStudentById() {
        return null;
    }

    @Override
    public GenericResponse saveStudent() {
        return null;
    }

    @Override
    public GenericResponse updateStudent() {
        return null;
    }
}
