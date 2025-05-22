package com.aethercubix.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.aethercubix.model.Marca;
import com.aethercubix.service.MarcaService;

@Controller
public class MarcaController {


    
    @Autowired
    private MarcaService marcaService;

    // Vista principal de marcas
    @GetMapping("/marcar")
    public String verMarcas(Model model) {
        model.addAttribute("marcas", marcaService.listarMarcas());
        return "marca"; // marca.html
    }

    // Mostrar formulario para nueva marca
    @GetMapping("/nuevamarca")
    public String mostrarFormularioNuevaMarca(Model model) {
        model.addAttribute("marca", new Marca());
        return "nuevamarca"; // nuevamarca.html
    }

@PostMapping("/guardarmarca")
public String guardarMarca(@ModelAttribute Marca marca, Model model) {
    if (marcaService.existeMarcaPorNombre(marca.getNombre())) {
        model.addAttribute("advertencia", "⚠️ Ya existe una marca con ese nombre.");
        model.addAttribute("marca", marca);
        return "nuevamarca";
    }

    marcaService.guardarMarca(marca);
    return "redirect:/marcar";
}


    // Eliminar marca con validación
    @GetMapping("/eliminarmarca/{id}")
    public String eliminarMarca(@PathVariable Long id, Model model) {
        try {
            marcaService.eliminarMarca(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("marcas", marcaService.listarMarcas());
            return "marca"; // Mostrar mensaje en misma vista
        }
        return "redirect:/marcar";
    }
    
}
