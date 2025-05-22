package com.aethercubix.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.aethercubix.service.ExcelExportService;



@Controller
@RequiredArgsConstructor
public class ReporteController {


    private final ExcelExportService excelExportService;

    @GetMapping("/reporteria")
    public ResponseEntity<byte[]> exportarExcel() throws Exception {
        byte[] excelBytes = excelExportService.exportAllTablesToExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ReporteCompleto.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelBytes);
    }
    
}
