package com.aethercubix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.aethercubix.model.MetodoPago;
import com.aethercubix.repository.MetodoPagoRepository;
import com.aethercubix.repository.VentaRepository;

@Controller
@RequestMapping("/pago")
public class MetodoPagoController {


     private final MetodoPagoRepository metodoPagoRepository;
    private final VentaRepository ventaRepository;

    public MetodoPagoController(MetodoPagoRepository metodoPagoRepository, VentaRepository ventaRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
        this.ventaRepository = ventaRepository;
    }

    // Mostrar lista de métodos de pago
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("metodos", metodoPagoRepository.findAll());
        return "pago"; // muestra pago.html
    }

    // Mostrar formulario para nuevo método de pago
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("metodoPago", new MetodoPago());
        return "nuevopago"; // muestra nuevopago.html
    }

    // Guardar nuevo método de pago
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute MetodoPago metodoPago, Model model) {
        if (metodoPagoRepository.findByNombre(metodoPago.getNombre()).isPresent()) {
            model.addAttribute("error", "El nombre ya existe.");
            return "nuevopago";
        }

        metodoPagoRepository.save(metodoPago);
        return "redirect:/pago";
    }

    // Eliminar un método de pago
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        MetodoPago metodo = metodoPagoRepository.findById(id).orElse(null);
        if (metodo == null) {
            redirectAttributes.addFlashAttribute("error", "Método no encontrado.");
            return "redirect:/pago";
        }

        boolean estaEnUso = ventaRepository.existsByMetodoPago(metodo);
        if (estaEnUso) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar. Este método está en uso.");
            return "redirect:/pago";
        }

        metodoPagoRepository.delete(metodo);
        return "redirect:/pago";
    }
    
}
