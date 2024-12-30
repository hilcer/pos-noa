package com.noa.pos.service;

import com.noa.pos.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface ExportService<T> {

    byte[] generateReport(List<T> lista) throws IOException;

}
