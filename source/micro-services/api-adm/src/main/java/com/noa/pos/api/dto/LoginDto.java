package com.noa.pos.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
