package com.manjitmentor.sms.controller;

import com.manjitmentor.sms.constant.APIPathConstants;
import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.repository.ApplicationUserRepository;
import com.manjitmentor.sms.request.SaveUserRequest;
import com.manjitmentor.sms.request.UpdateUserRequest;
import com.manjitmentor.sms.service.ApplicationUserService;
import com.manjitmentor.sms.service.impl.ApplicationUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(APIPathConstants.USERS)
public class ApplicationUserController {

    /*ApplicationUserController is dependent on ApplicationUserService to do it's jobs.
     We have created a variable for the class ApplicationUserService.*/

    private final ApplicationUserService applicationUserService; //This variable will work as an object now.

    /*This constructor below is a Dependency Injection!!
     Instead of initializing ApplicationUserService and it's implementation, it created a DI to access its
     implementations*/

    public ApplicationUserController(ApplicationUserService applicationUserService) {
        log.info("ApplicationUserController Constructor triggered!");
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

    @GetMapping(value = APIPathConstants.SharedOperations.ROLLBACK + "/" + APIPathConstants.PathVariable.USERID_WRAPPER,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> rollBackDeletedUsers
            (@PathVariable(APIPathConstants.PathVariable.USERID) Long id){
        GenericResponse genericResponse = applicationUserService.rollBackDeletedUsers(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = APIPathConstants.SharedOperations.ROLLBACK)
    public ResponseEntity<GenericResponse> rollBackAllDeletedUsers(){
        GenericResponse genericResponse = applicationUserService.rollBackAllDeletedUsers();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
}
