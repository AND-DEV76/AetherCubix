package com.aethercubix.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.aethercubix.model.Cliente;
import com.aethercubix.repository.ClienteRepository;
import com.aethercubix.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ClienteController {


     private final ClienteRepository clienteRepository;
     
@Autowired
private VentaRepository ventaRepository;


    @GetMapping("/Clientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "cliente"; // esto carga cliente.html
    }

    

@GetMapping("/Clientes/editar/{id}")
public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
    Cliente cliente = clienteRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));
    model.addAttribute("cliente", cliente);
    return "form_cliente";
}


@PostMapping("/Clientes/guardar")
public String guardarCliente(@ModelAttribute Cliente cliente, Model model) {
    Optional<Cliente> clienteExistente = clienteRepository.findByNit(cliente.getNit());

    if (clienteExistente.isPresent() &&
        !clienteExistente.get().getId_cliente().equals(cliente.getId_cliente())) {
        model.addAttribute("cliente", cliente);
        model.addAttribute("errorNit", "❌ Ya existe un cliente con ese NIT.");
        return "form_cliente";
    }

    clienteRepository.save(cliente);
    return "redirect:/Clientes";
}




@GetMapping("/Clientes/eliminar/{id}")
public String eliminarCliente(@PathVariable("id") Long id, Model model) {
    boolean tieneVentas = ventaRepository.existsVentaByClienteId(id);

    if (tieneVentas) {
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("errorEliminar", "❌ No se puede eliminar el cliente porque tiene ventas asociadas.");
        return "cliente";
    }

    clienteRepository.deleteById(id);
    return "redirect:/Clientes";
}



@GetMapping("/Clientes/nuevo")
public String mostrarFormularioNuevoCliente(Model model) {
    model.addAttribute("cliente", new Cliente());
    return "form_cliente";  // tu vista para el formulario de clientes
}

    
}
