package com.aethercubix.service;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aethercubix.model.Marca;
import com.aethercubix.repository.MarcaRepository;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    public void guardarMarca(Marca marca) {
        marcaRepository.save(marca);
    }

    public void eliminarMarca(Long id) throws Exception {
        if (tieneProductosAsociados(id)) {
            throw new Exception("No se puede eliminar la marca porque tiene productos asociados.");
        }
        marcaRepository.deleteById(id);
    }

    public boolean tieneProductosAsociados(Long idMarca) {
        // Implementación simple, se puede mejorar usando un query en un repositorio.
        return !marcaRepository.findById(idMarca).get().getProductos().isEmpty();
    }

    public Marca obtenerMarcaPorId(Long id) {
        return marcaRepository.findById(id).orElse(null);
    }


    public boolean existeMarcaPorNombre(String nombre) {
    return marcaRepository.existsByNombre(nombre);
}




    


}
