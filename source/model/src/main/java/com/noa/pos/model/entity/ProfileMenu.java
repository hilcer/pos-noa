package com.noa.pos.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProfileMenu {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "profile_menu_id", nullable = false)
    private Integer profileMenuId;
    @Basic
    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
    @Basic
    @Column(name = "menu_id", nullable = false)
    private Integer menuId;
    @Basic
    @Column(name = "last_user", nullable = false, length = 30)
    private String lastUser;
    @Basic
    @Column(name = "last_time", nullable = false)
    private Timestamp lastTime;
}
