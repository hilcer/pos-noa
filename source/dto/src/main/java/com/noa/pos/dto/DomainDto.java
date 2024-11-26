package com.noa.pos.dto;

import lombok.*;

import java.time.LocalDate;

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
    private LocalDate lastTime;

}
