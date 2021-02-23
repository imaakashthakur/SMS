package com.manjitmentor.sms.response;

import com.manjitmentor.sms.response.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter

public class UserListResponse implements Serializable {
    private List<UserDTO> users;
}
