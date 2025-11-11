package com.zonix.dndapp.dto.response;

import com.zonix.dndapp.dto.entity.UserDTO;

public record AuthResponse(
        String token,
        UserDTO userInfo
) {

}
