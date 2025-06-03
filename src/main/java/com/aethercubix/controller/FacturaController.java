package com.aethercubix.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aethercubix.dto.VentaDTO;
import com.aethercubix.model.Venta;
import com.aethercubix.repository.ClienteRepository;
import com.aethercubix.repository.EstadoVentaRepository;
import com.aethercubix.repository.MetodoPagoRepository;
import com.aethercubix.repository.ProductoRepository;
import com.aethercubix.service.FacturaService;
import com.aethercubix.service.VentaService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/factura")
@RequiredArgsConstructor
public class FacturaController {

     private final FacturaService facturaService;
    private final ClienteRepository clienteRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final EstadoVentaRepository estadoVentaRepository;
    private final ProductoRepository productoRepository;
    private final VentaService ventaService;

    @GetMapping
    public String mostrarFormularioFactura(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("metodosPago", metodoPagoRepository.findAll());
        model.addAttribute("estadosVenta", estadoVentaRepository.findAll());
        model.addAttribute("productos", productoRepository.findAll());
        return "factura";
    }

@PostMapping
public String guardarFactura(@RequestParam Long id_cliente,
                             @RequestParam Long id_metodo,
                             @RequestParam Long id_estado,
                             @RequestParam(value = "productosSeleccionados", required = false) List<Long> productoIds,
                             HttpServletRequest request,
                             Model model,
                             RedirectAttributes redirectAttributes) {

    // Validación: no hay productos seleccionados
    if (productoIds == null || productoIds.isEmpty()) {
        redirectAttributes.addFlashAttribute("error", "Debe seleccionar al menos un producto para realizar la venta.");
        return "redirect:/factura"; // redirige al formulario original
    }

    List<Integer> cantidades = new ArrayList<>();

    try {
        for (Long productoId : productoIds) {
            String paramName = "cantidad_" + productoId;
            String cantidadStr = request.getParameter(paramName);
            int cantidad = Integer.parseInt(cantidadStr);
            cantidades.add(cantidad);
        }

        Venta venta = facturaService.crearVenta(id_cliente, id_metodo, id_estado, productoIds, cantidades);
        model.addAttribute("venta", venta);
        return "venta"; // muestra la vista de la venta generada

    } catch (RuntimeException e) {
        // Captura errores como "Stock insuficiente"
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/factura"; // redirige nuevamente al formulario
    }
}





@GetMapping("/ventas")
public String listarVentas(@RequestParam(value = "filtro", required = false) String filtro, Model model) {
    List<VentaDTO> ventas;
    
    if (filtro != null && !filtro.isEmpty()) {
        ventas = ventaService.buscarVentas(filtro);
    } else {
        ventas = ventaService.listarTodas(); // o como se llame tu método por defecto
    }

    model.addAttribute("ventas", ventas);
    model.addAttribute("filtro", filtro); // para mantenerlo en el input
    return "sales"; // o el nombre real de tu sales.html
}



    
}
