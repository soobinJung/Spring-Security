package com.soobin.api.user.dto;

import com.soobin.api.user.domain.SoobinUser;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.pl.NIP;

@Data
@Builder
public class SoobinUserDto {

    private Long seq;
    private String email;
    private String password;

    public SoobinUser toEntity() {
        return SoobinUser.builder()
                .seq(this.seq)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
