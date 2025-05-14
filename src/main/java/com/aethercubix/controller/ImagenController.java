package com.aethercubix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aethercubix.service.ImagenService;




@Controller
public class ImagenController {


    @Autowired
    private ImagenService imagenService;

    @PostMapping("/imagenes/producto/{id}/subir")
public String subirImagen(@PathVariable("id") Integer idProducto,
                          @RequestParam("archivo") MultipartFile archivo) {
    try {
        imagenService.guardarImagen(archivo, idProducto);
        return "redirect:/productos"; // redirige al listado correcto
    } catch (Exception e) {
        e.printStackTrace();
        return "error";
    }
}


    
}
