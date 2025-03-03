package com.noa.pos.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DashboarDetailDto {

    private Integer userId;
    private Integer companyId;
    private String date;
}
