package com.aethercubix.controller;

import java.time.LocalDate;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.aethercubix.model.Proveedor;
import com.aethercubix.service.ProveedorService;


import org.springframework.ui.Model;




@Controller
public class ProveedorController {

    
    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping("/proveedores")
    public String verProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        return "proveedor"; // proveedor.html
    }

    @GetMapping("/proveedores/nuevo")
public String mostrarFormularioProveedor(Model model) {
    model.addAttribute("proveedor", new Proveedor());
    return "proveedor_form"; // El formulario HTML que vamos a crear
}


@PostMapping("/proveedores/guardar")
public String guardarProveedor(@ModelAttribute Proveedor proveedor, Model model) {
    System.out.println("Intentando guardar proveedor: " + proveedor.getEmail());

    if (proveedorService.existeEmail(proveedor.getEmail())) {
        model.addAttribute("error", "El correo ya está registrado.");
        System.out.println("Correo duplicado detectado.");
        return "proveedor_form";
    }

    proveedor.setFecha_registro(LocalDate.now());
    proveedorService.guardarProveedor(proveedor);
    System.out.println("Proveedor guardado correctamente.");
    return "redirect:/proveedores";
}


@GetMapping("/proveedor/editar/{id}")
public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
    Proveedor proveedor = proveedorService.obtenerProveedorPorId(id);
    if (proveedor != null) {
        model.addAttribute("proveedor", proveedor);
        return "proveedor_form"; // el mismo formulario
    } else {
        return "redirect:/proveedores";
    }
}


@PostMapping("/proveedor/actualizar/{id}")
public String actualizarProveedor(@PathVariable Long id, @ModelAttribute("proveedor") Proveedor proveedorActualizado) {
    Proveedor proveedorExistente = proveedorService.obtenerProveedorPorId(id);

    proveedorExistente.setNombre(proveedorActualizado.getNombre());
    proveedorExistente.setDireccion(proveedorActualizado.getDireccion());
    proveedorExistente.setTelefono(proveedorActualizado.getTelefono());
    proveedorExistente.setEmail(proveedorActualizado.getEmail());

    // No se modifica fecha_registro
    proveedorService.guardarProveedor(proveedorExistente);
    return "redirect:/proveedores";
}



// ahcer para eliminar si es seguro.

    
}
