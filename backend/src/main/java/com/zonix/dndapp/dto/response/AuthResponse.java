package com.zonix.dndapp.dto.response;

import com.zonix.dndapp.dto.entity.UserSafeInfo;

public record RegisterResponse(
        String token,
        UserSafeInfo userInfo
) {

}
