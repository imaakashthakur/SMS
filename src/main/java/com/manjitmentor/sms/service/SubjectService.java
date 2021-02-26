package com.manjitmentor.sms.service;

import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.request.SaveSubjectRequest;
import com.manjitmentor.sms.request.UpdateSubjectRequest;

public interface SubjectService {
    GenericResponse findActiveSubjects();

    GenericResponse findAllSubjects();

    GenericResponse findSubjectById(Long id);

    GenericResponse saveSubject(SaveSubjectRequest saveSubjectRequest);

    GenericResponse updateSubject(UpdateSubjectRequest request, Long id);

    GenericResponse deleteSubject(Long id);

    GenericResponse findDeletedSubjects();

    GenericResponse rollBackDeletedSubject(Long id);

    GenericResponse rollBackAllDeletedSubjects();
    
}
