package com.aethercubix.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aethercubix.dto.DetalleVentaDTO;
import com.aethercubix.dto.VentaDTO;
import com.aethercubix.model.DetalleVenta;
import com.aethercubix.model.Venta;
import com.aethercubix.repository.VentaRepository;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService {

    
    private final VentaRepository ventaRepository;

    public List<VentaDTO> obtenerTodasLasVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventas.stream().map(this::mapToDTO).toList();
    }

 public List<VentaDTO> buscarVentas(String filtro) {
    List<Venta> ventas = ventaRepository.findByFiltro(filtro);
    return ventas.stream().map(this::mapToDTO).toList();
}



public List<VentaDTO> listarTodas() {
    List<Venta> ventas = ventaRepository.findAll();
    return ventas.stream().map(this::mapToDTO).toList();
}

    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }

    private VentaDTO mapToDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setId_venta(venta.getId_venta());
        dto.setNombre_cliente(venta.getCliente().getNombre());
        dto.setNit_cliente(venta.getCliente().getNit());
        dto.setMetodo_pago(venta.getMetodoPago().getNombre());
        dto.setEstado(venta.getEstadoVenta().getNombre());
        dto.setTotal(venta.getTotal());

        List<DetalleVentaDTO> detalles = venta.getDetalles().stream().map(detalle -> {
            DetalleVentaDTO d = new DetalleVentaDTO();
            d.setProducto(detalle.getProducto().getNombre());
            d.setCantidad(detalle.getCantidad());
            d.setPrecio_unitario(detalle.getPrecio_unitario());
            return d;
        }).toList();

        dto.setDetalles(detalles);
        return dto;
    }
    

//metodos para pdf
    public Venta obtenerVentaPorId(Long id) {
    return ventaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
}



public ByteArrayOutputStream generarFacturaPdf(Venta venta) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    Document document = new Document(PageSize.A4);
    try {
        PdfWriter.getInstance(document, baos);
        document.open();

        Font tituloFont = new Font(Font.HELVETICA, 20, Font.BOLD);
        Font textoFont = new Font(Font.HELVETICA, 12);

        document.add(new Paragraph("Factura", tituloFont));
        document.add(new Paragraph(" ")); // espacio

        document.add(new Paragraph("Cliente: " + venta.getCliente().getNombre(), textoFont));
        document.add(new Paragraph("NIT: " + venta.getCliente().getNit(), textoFont));
        document.add(new Paragraph("Método de Pago: " + venta.getMetodoPago().getNombre(), textoFont));
        document.add(new Paragraph("Estado: " + venta.getEstadoVenta().getNombre(), textoFont));
        document.add(new Paragraph("Fecha: " + venta.getFecha_venta(), textoFont));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{4, 2, 2, 2});

        table.addCell("Producto");
        table.addCell("Precio");
        table.addCell("Cantidad");
        table.addCell("Subtotal");

        for (DetalleVenta d : venta.getDetalles()) {
            table.addCell(d.getProducto().getNombre());
            table.addCell("Q" + d.getPrecio_unitario());
            table.addCell(String.valueOf(d.getCantidad()));
            table.addCell("Q" + d.getSubtotal());
        }

        document.add(table);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Total: Q" + venta.getTotal(), textoFont));

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        document.close();
    }

    return baos;
}


}
