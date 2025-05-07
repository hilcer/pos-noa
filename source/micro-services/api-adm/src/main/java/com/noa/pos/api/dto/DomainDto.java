package com.noa.pos.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DomainDto {

    private Integer domainId;
    private String groupDom;
    private String name;
    private String value;
    private String description;
    private Boolean enabled;
    private String lastUser;
    private LocalDateTime lastTime;

}
