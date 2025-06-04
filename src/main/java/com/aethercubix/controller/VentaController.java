package com.aethercubix.controller;

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aethercubix.dto.VentaDTO;
import com.aethercubix.model.Venta;
import com.aethercubix.service.VentaService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

     private final VentaService ventaService;

    @GetMapping
    public String listarVentas(Model model, @RequestParam(name = "filtro", required = false) String filtro) {
        List<VentaDTO> ventas;

        if (filtro != null && !filtro.trim().isEmpty()) {
            ventas = ventaService.buscarVentas(filtro.trim());
        } else {
            ventas = ventaService.obtenerTodasLasVentas();
        }

        model.addAttribute("ventas", ventas);
        return "sales"; // Asegúrate que sales.html esté en src/main/resources/templates/
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return "redirect:/ventas";
    }



    @GetMapping("/pdf/{id}")
public void generarPdf(@PathVariable Long id, HttpServletResponse response) {
    try {
        Venta venta = ventaService.obtenerVentaPorId(id); // debes crear este método
        ByteArrayOutputStream baos = ventaService.generarFacturaPdf(venta);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=factura_" + id + ".pdf");
        response.setContentLength(baos.size());

        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Error al generar el PDF");
    }
}

    
    
}
