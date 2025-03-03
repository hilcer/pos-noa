package com.noa.pos.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DashboarReportDto implements Serializable {
    private List<Dashboard7DayDto> dashboard7DayDtos;
    private List<DashboardTopProductDto> dashboardTopProductDtos;
}
