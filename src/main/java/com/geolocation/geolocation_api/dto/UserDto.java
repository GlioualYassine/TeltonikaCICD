package com.geolocation.geolocation_api.dto;

import java.io.Serial;
import java.io.Serializable;

public class UserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 26L;
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
