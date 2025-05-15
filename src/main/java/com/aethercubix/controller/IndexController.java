package com.aethercubix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.aethercubix.model.Producto;
import com.aethercubix.repository.ProductoRepository;
import com.aethercubix.service.DashboardService;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {


    private final DashboardService dashboardService;

    public IndexController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }


    
    @Autowired
    private ProductoRepository productRepository;



@GetMapping("/")
public String dashboard(Model model) {
    // Top 5 más vendidos
    List<Map<String, Object>> top5 = dashboardService.getTop5ProductosMasVendidos();
    List<String> nombres = new ArrayList<>();
    List<Integer> cantidades = new ArrayList<>();
    for (Map<String, Object> item : top5) {
        nombres.add((String) item.get("nombre"));
        cantidades.add(((Number) item.get("totalVendido")).intValue());
    }

    // Más caros
    List<Producto> productosPorPrecio = productRepository.findTop5ByOrderByPrecioDesc();
    List<String> nombresPrecio = new ArrayList<>();
    List<Double> precios = new ArrayList<>();
    for (Producto p : productosPorPrecio) {
        nombresPrecio.add(p.getNombre());
        precios.add(p.getPrecio());
    }

    // Stock
    List<Producto> productosStock = productRepository.findTop5ByOrderByStockDesc();
    List<String> stockNombres = new ArrayList<>();
    List<Integer> stockCantidades = new ArrayList<>();
    for (Producto p : productosStock) {
        stockNombres.add(p.getNombre());
        stockCantidades.add(p.getStock());
    }

    model.addAttribute("nombres", nombres);
    model.addAttribute("cantidades", cantidades);
    model.addAttribute("nombresPrecio", nombresPrecio);
    model.addAttribute("precios", precios);
    model.addAttribute("stockNombres", stockNombres);
    model.addAttribute("stockCantidades", stockCantidades);

    return "index";
}

    
}
