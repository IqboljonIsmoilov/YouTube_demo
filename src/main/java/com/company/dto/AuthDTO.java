package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDTO {

    @NotNull(message = "Email required")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 6, max = 50, message = "Aboute me must be between 10 and 70 character")
    private String password;
}
