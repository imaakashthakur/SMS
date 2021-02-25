package com.manjitmentor.sms.service.impl;

import com.manjitmentor.sms.builder.ResponseBuilder;
import com.manjitmentor.sms.constant.MessagesConstants;
import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.model.ApplicationUser;
import com.manjitmentor.sms.model.Subject;
import com.manjitmentor.sms.repository.SubjectRepository;
import com.manjitmentor.sms.request.SaveSubjectRequest;
import com.manjitmentor.sms.request.UpdateSubjectRequest;
import com.manjitmentor.sms.response.dto.SubjectDTO;
import com.manjitmentor.sms.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public GenericResponse findAllSubjects() {
        final List<Subject> subjectList = subjectRepository.findAll();
        if(subjectList.isEmpty()){
            return ResponseBuilder.buildFailure(MessagesConstants.SUBJECT_NOT_FOUND);
        }
        List<SubjectDTO> response = new ArrayList<>();
        response = subjectList
                .stream()
                .map(subject -> modelMapper.map(subject, SubjectDTO.class))
                .collect(Collectors.toList());
        log.info("subjects: {}", response);
        return ResponseBuilder.buildSuccess(MessagesConstants.COURSE_FOUND, response);
    }

    @Override
    public GenericResponse findSubjectById(Long id) {
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        if(!subjectOptional.isPresent()){
            return ResponseBuilder.buildFailure(MessagesConstants.SUBJECT_NOT_FOUND);
        }
        if(subjectOptional.get().getIsActive() == 'N'){
            return ResponseBuilder.buildFailure(MessagesConstants.SUBJECT_WAS_DELETED);
        }
        else {
            SubjectDTO response = modelMapper.map(subjectOptional.get(), SubjectDTO.class);
            return ResponseBuilder.buildSuccess(MessagesConstants.SUBJECT_FOUND, response);
        }
    }

    @Override
    public GenericResponse saveSubject(SaveSubjectRequest request) {
        Subject subject = modelMapper.map(request, Subject.class);
        if(subject.getName().isEmpty() || subject.getDescription().isEmpty() || subject.getCode().isEmpty()){
            return ResponseBuilder.buildFailure(MessagesConstants.SUBJECT_CANT_BE_EMPTY);
        }
        subject.setCreatedBy(new ApplicationUser(1L));
        subject.setIsActive('Y');
        subjectRepository.save(subject);
        return ResponseBuilder.buildSuccess(MessagesConstants.SUBJECT_SAVED);
    }

    @Override
    public GenericResponse updateSubject(UpdateSubjectRequest request, Long id) {
        log.info("Entered updateSubject() method!");
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        log.info("SubjectOptional: {}", subjectOptional);
        if(!subjectOptional.isPresent()){
            return ResponseBuilder.buildFailure(MessagesConstants.SUBJECT_NOT_FOUND);
        }

        Subject subject = new Subject();
        subject = modelMapper.map(request, Subject.class);
        log.info("subjectResponse: {}", subject);
        if(subject.getName().isEmpty() || subject.getDescription().isEmpty() || subject.getCode().isEmpty()){
            return ResponseBuilder.buildFailure(MessagesConstants.SUBJECT_CANT_BE_EMPTY);
        }
        subject.setId(id);
        subject.setIsActive('Y');
        subject.setCreatedBy(new ApplicationUser(1L));
        subjectRepository.save(subject);
        return ResponseBuilder.buildSuccess(MessagesConstants.SUBJECT_SAVED);

    }

    @Override
    public GenericResponse deleteSubject(Long id) {

        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        if(!subjectOptional.isPresent()){
            return ResponseBuilder.buildFailure(MessagesConstants.SUBJECT_NOT_FOUND);
        }
        else{
            Subject subject = new Subject();
            subject = modelMapper.map(subjectOptional.get(), Subject.class);
            subject.setCreatedBy(new ApplicationUser(1L));
            subject.setIsActive('N');
            subjectRepository.save(subject);
            return ResponseBuilder.buildSuccess(MessagesConstants.SUBJECT_WAS_DELETED);
        }
    }

    @Override
    public GenericResponse findDeletedSubjects() {
        log.info("Entered findDeletedStudents() in StudentServiceImpl!");
        final List<Subject> subjectList = subjectRepository.findAll();
        if(subjectList.isEmpty()){
            return ResponseBuilder.buildFailure(MessagesConstants.SUBJECT_NOT_FOUND);
        }
        log.info("subjectList: {}", subjectList);

        List<SubjectDTO> subjectDTOList = new ArrayList<>();

        subjectDTOList = subjectList
                .stream()
                .map(sub -> modelMapper.map(sub, SubjectDTO.class))
                .collect(Collectors.toList());

        log.info("subjectList: {}", subjectList);

        List<SubjectDTO> subjectTrash = new ArrayList<>();

        for(SubjectDTO s: subjectDTOList){
            if(s.getIsActive() == 'N'){
                subjectTrash.add(s);
            }
        }

        log.info("subjectTrash: {}", subjectTrash);

        if(subjectTrash.isEmpty()){
            return ResponseBuilder.buildFailure(MessagesConstants.SUBJECT_NOT_FOUND);
        }
        else {
            return ResponseBuilder.buildSuccess(MessagesConstants.SUBJECT_FOUND, subjectTrash);
        }
    }
}

