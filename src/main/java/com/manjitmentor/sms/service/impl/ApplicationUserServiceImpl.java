package com.manjitmentor.sms.service.impl;

import com.manjitmentor.sms.builder.ResponseBuilder;
import com.manjitmentor.sms.constant.MessagesConstants;
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
import com.manjitmentor.sms.response.UserListResponse;

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
    public GenericResponse getAllApplicationUser() {

        final List<ApplicationUser> applicationUsers = applicationUserRepository.findAll();

        if(applicationUsers.isEmpty()){
            return ResponseBuilder.buildFailure(MessagesConstants.USER_NOT_FOUND);
        }

        UserListResponse userListResponse = new UserListResponse();

        List<UserDTO> userDTOList = new ArrayList<>();

        userDTOList = applicationUsers
                .stream()
                .map(applicationUser -> modelMapper.map(applicationUser, UserDTO.class))
                .collect(Collectors.toList());


        return ResponseBuilder.buildSuccess(MessagesConstants.USER_FOUND, userDTOList);
    }

    @Override
    public GenericResponse getApplicationUserById(long id) {
        Optional<ApplicationUser> applicationUser = applicationUserRepository.findById(id);
        if(!applicationUser.isPresent()){
            return ResponseBuilder.buildFailure("Could not find the user.");
        }
        else{
            UserDTO response = modelMapper.map(applicationUser.get(), UserDTO.class);
            if(response.getIsActive() == 'N'){
                return ResponseBuilder.buildFailure(MessagesConstants.USER_WAS_DELETED);
            }

            return ResponseBuilder.buildSuccess(MessagesConstants.USER_FOUND, response);
        }
    }

    @Override
    public GenericResponse saveApplicationUser(SaveUserRequest request) {
        ApplicationUser applicationUser = modelMapper.map(request, ApplicationUser.class);
        applicationUser.setCreatedBy(new ApplicationUser(1L));
        log.info("User save: {}", applicationUser);

        applicationUserRepository.save(applicationUser);

        return ResponseBuilder.buildSuccess(MessagesConstants.USER_SAVED);
    }

    @Override
    public GenericResponse updateApplicationUser(Long id, UpdateUserRequest request) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(id);
        if(!applicationUserOptional.isPresent()){
            return ResponseBuilder.buildFailure(MessagesConstants.USER_NOT_FOUND);
        }

        else {
            ApplicationUser applicationUser = modelMapper.map(request, ApplicationUser.class);
            applicationUser.setId(id);
            applicationUser.setCreatedBy(new ApplicationUser(1L));

            applicationUserRepository.save(applicationUser);

            return ResponseBuilder.buildSuccess(MessagesConstants.USER_UPDATED);
        }

    }

    @Override
    public GenericResponse deleteApplicationUser(Long id) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(id);
        log.info("Optional: {}", applicationUserOptional);
        if(!applicationUserOptional.isPresent()){
            return ResponseBuilder.buildFailure(MessagesConstants.USER_NOT_FOUND);
        }

        else{
            ApplicationUser applicationUser = new ApplicationUser();
            applicationUser = modelMapper.map(applicationUserOptional.get(), ApplicationUser.class);
            log.info("After mapping to ApplicationUSer: {}", applicationUser);
            applicationUser.setIsActive('N');
            applicationUserRepository.save(applicationUser);
            log.info("After saving to repository: {}", applicationUserRepository);
            return ResponseBuilder.buildSuccess(MessagesConstants.USER_WAS_DELETED);
        }
    }

}
