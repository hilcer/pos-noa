package com.noa.pos.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer profileId;
    @Basic
    @Column(name = "company_id", nullable = true)
    private Integer companyId;
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = 100)
    private String description;
    @Basic
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
    @Basic
    @Column(name = "last_user", nullable = false, length = 30)
    private String lastUser;
    @Basic
    @Column(name = "last_time", nullable = false)
    private Timestamp lastTime;

}
