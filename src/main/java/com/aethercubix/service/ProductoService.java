package com.aethercubix.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aethercubix.model.Imagen;
import com.aethercubix.model.Producto;
import com.aethercubix.repository.ImagenRepository;
import com.aethercubix.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ImagenRepository imagenRepository;

    private final String rutaImagenes = "src/main/resources/static/img/";


    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> listar() {
        return productoRepository.findAll();



    }


    public Producto save(Producto producto) {
    return productoRepository.save(producto);  // Esto guardará el producto junto con el id_proveedor
}


 
public void eliminarProductoConImagen(Long idProducto) {
    // Obtener la imagen asociada
    List<Imagen> imagenes = imagenRepository.findByProductoId(idProducto);
    Imagen imagen = (imagenes != null && !imagenes.isEmpty()) ? imagenes.get(0) : null;

    // Eliminar imagen física si existe
    if (imagen != null) {
        File archivo = new File(rutaImagenes + imagen.getNombreImagen());
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    // Eliminar producto (imagen se elimina en cascada en la BD)
    productoRepository.deleteById(idProducto);
}

}
