package com.zonix.dndapp.dto.response;

import com.zonix.dndapp.dto.entity.UserSafeInfo;

public record AuthResponse(
        String token,
        UserSafeInfo userInfo
) {

}
