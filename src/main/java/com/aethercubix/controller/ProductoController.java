package com.aethercubix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aethercubix.model.Producto;
import com.aethercubix.repository.MarcaRepository;
import com.aethercubix.repository.ProductoRepository;
import com.aethercubix.repository.ProveedorRepository;
import com.aethercubix.service.ProductoService;




@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
private MarcaRepository marcaRepository;


@GetMapping
public String listarProductos(@RequestParam(value = "buscar", required = false) String buscar, Model model) {
    List<Producto> productos;

    if (buscar != null && !buscar.isBlank()) {
        productos = productoRepository.buscarProductos(buscar);
    } else {
        productos = productoRepository.findAll();
    }

    for (Producto producto : productos) {
        producto.getImagenes().size(); // cargar imágenes
    }

    model.addAttribute("productos", productos);
    return "producto";
}


    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("proveedores", proveedorRepository.findAll());
        model.addAttribute("marcas", marcaRepository.findAll());//despues de la normalizacion

        return "nuevoproducto";
    }


    @PostMapping("/guardar")
public String guardarProducto(@ModelAttribute Producto producto) {
    System.out.println("ID del proveedor recibido: " + producto.getProveedor().getId_proveedor());
    productoService.save(producto);
    return "redirect:/productos";
}


@GetMapping("/editar/{id}")
public String mostrarFormularioEditarProducto(@PathVariable("id") Integer id, Model model) {
    Producto producto = productoRepository.findById(id.longValue())
                            .orElseThrow(() -> new IllegalArgumentException("ID de producto inválido: " + id));
    model.addAttribute("producto", producto);
    model.addAttribute("proveedores", proveedorRepository.findAll()); // si necesitas lista de proveedores
    model.addAttribute("marcas", marcaRepository.findAll());//despues de la normalizacion

    return "editarproducto"; // vista que vas a crear
}


@PostMapping("/actualizar")
public String actualizarProducto(@ModelAttribute Producto producto) {
    productoService.save(producto); // reutilizás el método `save` del servicio
    return "redirect:/productos";
}



@GetMapping("/eliminar/{id}")
public String eliminarProducto(@PathVariable("id") Long id) {
    productoService.eliminarProductoConImagen(id);
    return "redirect:/productos";
}




     
}
