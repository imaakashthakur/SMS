package com.manjitmentor.sms.controller;

import com.manjitmentor.sms.constant.APIPathConstants;
import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.request.SaveSubjectRequest;
import com.manjitmentor.sms.request.UpdateSubjectRequest;
import com.manjitmentor.sms.service.impl.SubjectServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIPathConstants.SUBJECTS)
public class SubjectController {
    private final SubjectServiceImpl subjectServiceImpl;
    public SubjectController(SubjectServiceImpl subjectServiceImpl) {
        this.subjectServiceImpl = subjectServiceImpl;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findAllSubjects(){
       GenericResponse genericResponse = subjectServiceImpl.findAllSubjects();
       return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = APIPathConstants.PathVariable.SUBJECTID_WRAPPER
            ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findSubjectById(@PathVariable(APIPathConstants.PathVariable.SUBJECTID) Long id){
       GenericResponse genericResponse = subjectServiceImpl.findSubjectById(id);
       return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping(value = APIPathConstants.SharedOperations.SAVE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> saveSubject(@RequestBody SaveSubjectRequest request){
       GenericResponse genericResponse = subjectServiceImpl.saveSubject(request);
       return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping(value = APIPathConstants.SharedOperations.UPDATE +
            "/" + APIPathConstants.PathVariable.SUBJECTID_WRAPPER,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> updateSubject(@PathVariable(APIPathConstants.PathVariable.SUBJECTID) @RequestBody UpdateSubjectRequest request, Long id){
       GenericResponse genericResponse = subjectServiceImpl.updateSubject(request, id);
       return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = APIPathConstants.SharedOperations.DELETE + "/" + APIPathConstants.PathVariable.SUBJECTID_WRAPPER)
    public ResponseEntity<GenericResponse> deleteSubject(@PathVariable(APIPathConstants.PathVariable.SUBJECTID) Long id){
       GenericResponse genericResponse = subjectServiceImpl.deleteSubject(id);
       return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = APIPathConstants.SharedOperations.TRASH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findDeletedSubjects(){
        GenericResponse genericResponse = subjectServiceImpl.findDeletedSubjects();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
}
