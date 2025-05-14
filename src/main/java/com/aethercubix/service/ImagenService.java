package com.aethercubix.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aethercubix.model.Imagen;
import com.aethercubix.model.Producto;
import com.aethercubix.repository.ImagenRepository;
import com.aethercubix.repository.ProductoRepository;

@Service
public class ImagenService {

    // Directorio donde se guardará la imagen (nota: en entornos de producción conviene otro mecanismo)
    private static final String UPLOAD_DIR = "src/main/resources/static/img/";

    @Autowired
    private ImagenRepository imagenRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public void guardarImagen(MultipartFile archivo, Integer idProducto) throws IOException {
        // Verifica que el producto exista
        Producto producto = productoRepository.findById(idProducto.longValue())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id " + idProducto));

        // Obtén el nombre original del archivo
        String nombreOriginal = archivo.getOriginalFilename();
        if (nombreOriginal == null || nombreOriginal.isBlank()) {
            throw new IllegalArgumentException("El nombre del archivo es inválido");
        }

        // Para evitar conflictos, puedes renombrar el archivo. Por ejemplo, concatenando el idProducto y la fecha
        String nuevoNombre = "producto_" + idProducto + "_" + System.currentTimeMillis() + 
                             nombreOriginal.substring(nombreOriginal.lastIndexOf('.'));

        // Ruta destino para guardar la imagen
        Path rutaDestino = Paths.get(UPLOAD_DIR).resolve(nuevoNombre).toAbsolutePath();

        // Crea el directorio si no existe
        Files.createDirectories(rutaDestino.getParent());

        // Copia el archivo en el destino
        Files.copy(archivo.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);

        // Crea un registro en la base de datos
        Imagen imagen = new Imagen();
        imagen.setNombreImagen(nuevoNombre);
        imagen.setProducto(producto);

        imagenRepository.save(imagen);
    }
    
}
