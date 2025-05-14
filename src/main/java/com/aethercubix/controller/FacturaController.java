package com.aethercubix.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aethercubix.model.Venta;
import com.aethercubix.repository.ProductoRepository;
import com.aethercubix.service.FacturaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FacturaController {


     private final ProductoRepository productoRepository;
    private final FacturaService facturaService;

    @GetMapping("/factura")
    public String mostrarFormularioFactura(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "factura";
    }


    @PostMapping("/factura")
public String procesarFactura(
        @RequestParam String nombre_cliente,
        @RequestParam String nit_cliente,
        @RequestParam String metodo_pago,
        @RequestParam String estado,
        @RequestParam List<Long> productoIds,
        @RequestParam List<Integer> cantidades,
        Model model
) {
    try {
        Venta venta = new Venta();
        venta.setNombre_cliente(nombre_cliente);
        venta.setNit_cliente(nit_cliente);
        venta.setFecha_venta(LocalDate.now());
        venta.setMetodo_pago(metodo_pago);
        venta.setEstado(estado);

        Venta guardada = facturaService.guardarFactura(venta, productoIds, cantidades);
        model.addAttribute("venta", guardada);
        return "venta";
    } catch (RuntimeException e) {
        model.addAttribute("error", e.getMessage());
        model.addAttribute("productos", productoRepository.findAll());
        return "factura"; // vuelve a mostrar el formulario con el error
    }
}


@GetMapping("/ventas")
public String verVentas(Model model) {
    List<Venta> ventas = facturaService.obtenerTodasLasVentas();
    model.addAttribute("ventas", ventas);
    return "sales";
}


@GetMapping("/ventas/eliminar/{id}")
public String eliminarVenta(@PathVariable Long id) {
    facturaService.eliminarVenta(id);
    return "redirect:/ventas";
}


    
}
