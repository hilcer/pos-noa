package com.noa.pos.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "menu")
public class MenuEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "menu_id", nullable = false)
    private Integer menuId;
    @Basic
    @Column(name = "parent_id", nullable = true)
    private Integer parentId;
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic
    @Column(name = "path", nullable = true, length = 200)
    private String path;
    @Basic
    @Column(name = "icon", nullable = true, length = 50)
    private String icon;
    @Basic
    @Column(name = "enabled", nullable = true)
    private Boolean enabled;
    @Basic
    @Column(name = "sequence", nullable = true)
    private Integer sequence;
    @Basic
    @Column(name = "last_user", nullable = false, length = 30)
    private String lastUser;
    @Basic
    @Column(name = "last_time", nullable = false)
    private Timestamp lastTime;

}
