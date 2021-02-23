package com.manjitmentor.sms.constant;

public interface APIPathConstants {
    String USERS = "users";

    interface PathVariable{
        String USERID_WRAPPER = "{userId}";
        String COURSEID_WRAPPER = "{courseId}";
        String USERID = "userId";
        String COURSEID = "courseId";
    }

    interface SharedOperations{
        String SAVE ="save";
        String UPDATE = "update";
        String DELETE = "delete";
    }
}
