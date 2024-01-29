package com.soobin.api.user.domain;

import com.soobin.api.user.dto.SoobinUserDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class SoobinUser {

    @Id @GeneratedValue
    Long seq;
    String email;
    String password;

    public SoobinUserDto toDto() {
        return SoobinUserDto.builder()
                .seq(this.seq)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
