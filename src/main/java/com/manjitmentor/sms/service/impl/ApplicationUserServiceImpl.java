package com.manjitmentor.sms.service.impl;

import com.manjitmentor.sms.builder.ResponseBuilder;
import com.manjitmentor.sms.constant.ResponseMsgConstant;
import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.model.ApplicationUser;
import com.manjitmentor.sms.repository.ApplicationUserRepository;
import com.manjitmentor.sms.request.SaveUserRequest;
import com.manjitmentor.sms.request.UpdateUserRequest;
import com.manjitmentor.sms.response.dto.UserDTO;
import com.manjitmentor.sms.service.ApplicationUserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ApplicationUserServiceImpl(ApplicationUserRepository applicationUserRepository, ModelMapper modelMapper) {
        this.applicationUserRepository = applicationUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GenericResponse getActiveApplicationUser(){
        final List<ApplicationUser> allApplicationUsers = applicationUserRepository.findAll();
        if(allApplicationUsers.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        }
        List<UserDTO> userDTOList = new ArrayList<>();

        userDTOList = allApplicationUsers.stream()
                .map(applicationUser -> modelMapper.map(applicationUser, UserDTO.class))
                .collect(Collectors.toList());

        List<UserDTO> activeUsers = new ArrayList<>();

        for(UserDTO u : userDTOList){
            if(u.getIsActive() == 'Y'){
                activeUsers.add(u);
            }
        }

        log.info("activeUsers: {}", activeUsers);

        return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_FOUND, activeUsers);
    }



    @Override
    public GenericResponse getAllApplicationUser() {

        final List<ApplicationUser> applicationUsers = applicationUserRepository.findAll();
        log.info("applicationUsers: {}", applicationUsers);

        if(applicationUsers.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        }

        List<UserDTO> userDTOList = new ArrayList<>();


        userDTOList = applicationUsers
                .stream()
                .map(applicationUser -> modelMapper.map(applicationUser, UserDTO.class))
                .collect(Collectors.toList());


        return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_FOUND, userDTOList);
    }

    @Override
    public GenericResponse getApplicationUserById(Long id) {
        Optional<ApplicationUser> applicationUser = applicationUserRepository.findById(id);
        log.info("applicationUser: {}", applicationUser);
        if(!applicationUser.isPresent()){
            return ResponseBuilder.buildFailure("Could not find the user.");
        }
        else{
            UserDTO response = modelMapper.map(applicationUser.get(), UserDTO.class);
            if(response.getIsActive() == 'N'){
                return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_WAS_DELETED);
            }

            return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_FOUND, response);
        }
    }

    @Override
    public GenericResponse saveApplicationUser(SaveUserRequest request) {
        ApplicationUser applicationUser = modelMapper.map(request, ApplicationUser.class);
        applicationUser.setCreatedBy(new ApplicationUser(1L));
        applicationUser.setIsActive('Y');

        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();

        for(ApplicationUser a : applicationUserList){
            if(a.getEmailAddress().equals(request.getEmailAddress())){
                return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_ALREADY_PRESENT);
            }
        }

        if(applicationUser.getFirstName().isEmpty() || applicationUser.getLastName().isEmpty() ||
                applicationUser.getPassword().isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_CANT_BE_EMPTY);
        }

        applicationUserRepository.save(applicationUser);

        return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_SAVED);

    }

    @Override
    public GenericResponse updateApplicationUser(Long id, UpdateUserRequest request) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(id);
        if(!applicationUserOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        }

        else {
            ApplicationUser applicationUser = modelMapper.map(request, ApplicationUser.class);

            if(applicationUser.getFirstName().isEmpty() || applicationUser.getLastName().isEmpty() ||
                    applicationUser.getPassword().isEmpty()){
                return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_CANT_BE_EMPTY);
            }

            applicationUser.setId(id);
            applicationUser.setCreatedBy(new ApplicationUser(1L));
            applicationUser.setIsActive('Y');
            applicationUserRepository.save(applicationUser);

            return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_UPDATED);
        }

    }

    @Override
    public GenericResponse deleteApplicationUser(Long id) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(id);
        log.info("Optional: {}", applicationUserOptional);
        if(!applicationUserOptional.isPresent()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        }

        else{
            ApplicationUser applicationUser = new ApplicationUser();
            applicationUser = modelMapper.map(applicationUserOptional.get(), ApplicationUser.class);
            applicationUser.setIsActive('N');
            applicationUserRepository.save(applicationUser);
            return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_WAS_DELETED);
        }
    }

    @Override
    public GenericResponse findDeletedUsers() {
        final List<ApplicationUser> applicationUsers = applicationUserRepository.findAll();
        if(applicationUsers.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NOT_FOUND);
        }
        List<UserDTO> userDTOList = new ArrayList<>();

        userDTOList = applicationUsers
                .stream()
                .map(appUsers -> modelMapper.map(appUsers, UserDTO.class))
                .collect(Collectors.toList());

        log.info("userDTOList: {}", userDTOList);

        List<UserDTO> usersTrash = new ArrayList<>();

        for (UserDTO userDTO: userDTOList) {
            if(userDTO.getIsActive() == 'N'){
                usersTrash.add(userDTO);
            }
        }
        log.info("Trash: {}", usersTrash);

        if(usersTrash.isEmpty()){
            return ResponseBuilder.buildFailure(ResponseMsgConstant.USER_NO_TRASH);
        }
        else{
            return ResponseBuilder.buildSuccess(ResponseMsgConstant.USER_FOUND, usersTrash);
        }

    }

}

