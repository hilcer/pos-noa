package com.noa.pos.api.dto;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProfileMenuDto {
    private Integer profileMenuId;
    private Integer profileId;
    private Integer menuId;
    private String lastUser;
    private Timestamp lastTime;
}
