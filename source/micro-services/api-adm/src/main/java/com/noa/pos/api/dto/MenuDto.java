package com.noa.pos.api.dto;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MenuDto {
    private Integer menuId;
    private Integer parentId;
    private String name;
    private String path;
    private String icon;
    private Boolean enabled;
    private Integer sequence;
    private String lastUser;
    private Timestamp lastTime;

}
