package com.soobin.api.user.dto.req;

import com.soobin.api.user.dto.SoobinUserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqSignUp {

    @NotBlank(message = "User ID is required.")
    @Size(min = 4, max = 12, message = "User ID must be between 4 and 12 characters.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 4, message = "Password must be at least 4 characters.")
    private String password;

    public SoobinUserDto toDto(){

        System.out.println("ReqSignUp.toDto() email: " + this.email);
        System.out.println("ReqSignUp.toDto() password: " + this.password);

        return SoobinUserDto.builder()
                .email( this.email )
                .password( this.password )
                .build();
    }
}
