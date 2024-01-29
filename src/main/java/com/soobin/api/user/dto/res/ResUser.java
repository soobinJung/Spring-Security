package com.soobin.api.user.dto.res;

import com.soobin.api.user.dto.SoobinUserDto;
import lombok.Getter;

@Getter
public class ResUser {

    String email;

    public ResUser(SoobinUserDto dto){
        this.email = dto.getEmail();
    }
}
