package com.aethercubix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import java.util.List;

import com.aethercubix.model.EstadoVenta;
import com.aethercubix.repository.EstadoVentaRepository;
import com.aethercubix.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/estado")
@RequiredArgsConstructor
public class EstadoVentaController {


    private final EstadoVentaRepository estadoVentaRepo;
    private final VentaRepository ventaRepo;

    // LISTAR
    @GetMapping
    public String listarEstados(Model model) {
        List<EstadoVenta> estados = estadoVentaRepo.findAll();
        model.addAttribute("estados", estados);
        return "estado";
    }

    // FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoEstado(Model model) {
        model.addAttribute("estadoVenta", new EstadoVenta());
        return "nuevoestado";
    }

    // GUARDAR NUEVO
    @PostMapping("/guardar")
    public String guardarEstado(@ModelAttribute EstadoVenta estadoVenta, Model model) {
        if (estadoVentaRepo.findByNombreIgnoreCase(estadoVenta.getNombre()).isPresent()) {
            model.addAttribute("error", "El nombre del estado ya existe.");
            return "nuevoestado";
        }
        estadoVentaRepo.save(estadoVenta);
        return "redirect:/estado";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarEstado(@PathVariable Long id, Model model) {
        EstadoVenta estado = estadoVentaRepo.findById(id).orElse(null);
        if (estado != null) {
            boolean enUso = ventaRepo.existsByEstadoVenta(estado);
            if (enUso) {
                model.addAttribute("error", "No se puede eliminar, el estado está en uso en una venta.");
                return listarEstados(model);
            }
            estadoVentaRepo.delete(estado);
        }
        return "redirect:/estado";
    }
    
}
