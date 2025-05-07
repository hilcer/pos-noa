package com.noa.pos.api.dto;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProfileDto {
    private Integer profileId;
    private Integer companyId;
    private String name;
    private String description;
    private Boolean enabled;
    private String lastUser;
    private Timestamp lastTime;

}
