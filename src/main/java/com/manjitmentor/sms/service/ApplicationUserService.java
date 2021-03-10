package com.manjitmentor.sms.service;

import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.request.SaveUserRequest;
import com.manjitmentor.sms.request.UpdateUserRequest;

public interface ApplicationUserService {

    GenericResponse getActiveApplicationUser();

    GenericResponse getAllApplicationUser();

    GenericResponse getApplicationUserById(Long id);

    GenericResponse saveApplicationUser(SaveUserRequest request);

    GenericResponse updateApplicationUser(Long id, UpdateUserRequest request);

    GenericResponse deleteApplicationUser(Long id);

    GenericResponse findDeletedUsers();

    GenericResponse rollBackDeletedUsers(Long id);

    GenericResponse rollBackAllDeletedUsers();
}
