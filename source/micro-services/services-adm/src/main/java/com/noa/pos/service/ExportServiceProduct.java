package com.noa.pos.service;

import com.noa.pos.model.dto.ReportOrderSalesProdDto;

import java.io.IOException;
import java.util.List;

public interface ExportServiceProduct {

    byte[] generateReportByProd(List<ReportOrderSalesProdDto> lista) throws IOException;

}
