package com.manjitmentor.sms.controller;

import com.manjitmentor.sms.constant.APIPathConstants;
import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.request.SaveUserRequest;
import com.manjitmentor.sms.request.UpdateUserRequest;
import com.manjitmentor.sms.service.ApplicationUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIPathConstants.USERS)
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;

    public ApplicationUserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findActiveUsers(){
        GenericResponse genericResponse = applicationUserService.getActiveApplicationUser();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);

    }

    @GetMapping(value = APIPathConstants.SharedOperations.ALL,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findAllUsers(){
        GenericResponse genericResponse = applicationUserService.getAllApplicationUser();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = APIPathConstants.PathVariable.USERID_WRAPPER,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findUserById(@PathVariable(APIPathConstants.PathVariable.USERID) Long id){
        GenericResponse genericResponse = applicationUserService.getApplicationUserById(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping(value = APIPathConstants.SharedOperations.SAVE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> saveApplicationUser(@RequestBody SaveUserRequest request){
        GenericResponse genericResponse = applicationUserService.saveApplicationUser(request);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping(value = APIPathConstants.SharedOperations.UPDATE + "/" + APIPathConstants.PathVariable.USERID_WRAPPER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> updateApplicationUser
            (@PathVariable(APIPathConstants.PathVariable.USERID) Long id,
             @RequestBody UpdateUserRequest request){
        GenericResponse genericResponse = applicationUserService.updateApplicationUser(id, request);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = APIPathConstants.SharedOperations.DELETE + "/" + APIPathConstants.PathVariable.USERID_WRAPPER)
    public ResponseEntity<GenericResponse> deleteApplicationUser
            (@PathVariable(APIPathConstants.PathVariable.USERID) Long id){
        GenericResponse genericResponse = applicationUserService.deleteApplicationUser(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }


    @GetMapping(value = APIPathConstants.SharedOperations.TRASH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findDeletedUsers(){
        GenericResponse genericResponse = applicationUserService.findDeletedUsers();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

}
