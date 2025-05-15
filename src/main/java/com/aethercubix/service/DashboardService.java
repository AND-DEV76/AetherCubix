package com.aethercubix.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aethercubix.repository.DetalleVentaRepository;


        
    @Service
    public class DashboardService {

            private final DetalleVentaRepository detalleVentaRepository;

        public DashboardService(DetalleVentaRepository detalleVentaRepository) {
            this.detalleVentaRepository = detalleVentaRepository;
        }

        public List<Map<String, Object>> getTop5ProductosMasVendidos() {
            List<Object[]> resultados = detalleVentaRepository.findTop5ProductosMasVendidos((org.springframework.data.domain.Pageable) PageRequest.of(0, 5));
            List<Map<String, Object>> topProductos = new ArrayList<>();

            for (Object[] fila : resultados) {
                Map<String, Object> map = new HashMap<>();
                map.put("nombre", fila[0]);
                map.put("totalVendido", fila[1]);
                topProductos.add(map);
            }

            return topProductos;
        }
    



}
