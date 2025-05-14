package com.aethercubix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aethercubix.model.Proveedor;
import com.aethercubix.repository.ProductoRepository;
import com.aethercubix.repository.ProveedorRepository;
import java.util.List;
import java.util.Optional;



@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;


    @Autowired
    private ProductoRepository productoRepository;

  

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

        public void guardarProveedor(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
    }

    public Proveedor obtenerProveedorPorId(Long id) {
        Optional<Proveedor> proveedorOptional = proveedorRepository.findById(id);
        return proveedorOptional.orElse(null); // Devuelve null si no se encuentra el proveedor
    }

    public boolean existeEmail(String email) {
    return proveedorRepository.findByEmail(email).isPresent();
}



 public boolean eliminarProveedorSiNoTieneProductos(Integer idProveedor) {
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(idProveedor.longValue());
        if (proveedorOpt.isPresent()) {
            Proveedor proveedor = proveedorOpt.get();
            long cantidadProductos = productoRepository.countByProveedor(proveedor);
            if (cantidadProductos == 0) {
                proveedorRepository.delete(proveedor);
                return true; // Eliminado correctamente
            } else {
                return false; // No se puede eliminar
            }
        }
        return false; // No encontrado
    }


    
    
}
