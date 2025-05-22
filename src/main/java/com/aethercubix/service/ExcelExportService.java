package com.aethercubix.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.aethercubix.model.Cliente;
import com.aethercubix.model.DetalleVenta;
import com.aethercubix.model.EstadoVenta;
import com.aethercubix.model.Imagen;
import com.aethercubix.model.Marca;
import com.aethercubix.model.MetodoPago;
import com.aethercubix.model.Producto;
import com.aethercubix.model.Proveedor;
import com.aethercubix.model.Venta;
import com.aethercubix.repository.ClienteRepository;
import com.aethercubix.repository.DetalleVentaRepository;
import com.aethercubix.repository.EstadoVentaRepository;
import com.aethercubix.repository.ImagenRepository;
import com.aethercubix.repository.MarcaRepository;
import com.aethercubix.repository.MetodoPagoRepository;
import com.aethercubix.repository.ProductoRepository;
import com.aethercubix.repository.ProveedorRepository;
import com.aethercubix.repository.VentaRepository;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.ByteArrayOutputStream;

import java.util.List;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class ExcelExportService {

        private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final MarcaRepository marcaRepository;
    private final ImagenRepository imagenRepository;
    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final EstadoVentaRepository estadoVentaRepository;

    public byte[] exportAllTablesToExcel() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {

            // 1. Clientes
            Sheet clientesSheet = workbook.createSheet("Clientes");
            createHeaderRow(clientesSheet, "ID", "Nombre", "NIT");
            List<Cliente> clientes = clienteRepository.findAll();
            int row = 1;
            for (Cliente c : clientes) {
                Row r = clientesSheet.createRow(row++);
                r.createCell(0).setCellValue(c.getId_cliente());
                r.createCell(1).setCellValue(c.getNombre());
                r.createCell(2).setCellValue(c.getNit());
            }

            // 2. Proveedores
            Sheet proveedorSheet = workbook.createSheet("Proveedores");
            createHeaderRow(proveedorSheet, "ID", "Nombre", "Contacto", "Teléfono", "Email", "Dirección", "Fecha Registro");
            row = 1;
            for (Proveedor p : proveedorRepository.findAll()) {
                Row r = proveedorSheet.createRow(row++);
                r.createCell(0).setCellValue(p.getId_proveedor());
                r.createCell(1).setCellValue(p.getNombre());
                r.createCell(2).setCellValue(p.getContacto());
                r.createCell(3).setCellValue(p.getTelefono());
                r.createCell(4).setCellValue(p.getEmail());
                r.createCell(5).setCellValue(p.getDireccion());
                r.createCell(6).setCellValue(p.getFecha_registro().toString());
            }

            // 3. Marcas
            Sheet marcaSheet = workbook.createSheet("Marcas");
            createHeaderRow(marcaSheet, "ID", "Nombre");
            row = 1;
            for (Marca m : marcaRepository.findAll()) {
                Row r = marcaSheet.createRow(row++);
                r.createCell(0).setCellValue(m.getId());
                r.createCell(1).setCellValue(m.getNombre());
            }

            // 4. Productos
            Sheet productoSheet = workbook.createSheet("Productos");
            createHeaderRow(productoSheet, "ID", "Nombre", "Marca", "Precio", "Stock", "Descripción", "Proveedor");
            row = 1;
            for (Producto p : productoRepository.findAll()) {
                Row r = productoSheet.createRow(row++);
                r.createCell(0).setCellValue(p.getId_producto());
                r.createCell(1).setCellValue(p.getNombre());
                r.createCell(2).setCellValue(p.getMarca().getNombre());
                r.createCell(3).setCellValue(p.getPrecio());
                r.createCell(4).setCellValue(p.getStock());
                r.createCell(5).setCellValue(p.getDescripcion());
                r.createCell(6).setCellValue(p.getProveedor().getNombre());
            }

            // 5. Imagenes
            Sheet imagenSheet = workbook.createSheet("Imagenes");
            createHeaderRow(imagenSheet, "ID", "Nombre Imagen", "Producto");
            row = 1;
            for (Imagen i : imagenRepository.findAll()) {
                Row r = imagenSheet.createRow(row++);
                r.createCell(0).setCellValue(i.getId_imagen());
                r.createCell(1).setCellValue(i.getNombreImagen());
                r.createCell(2).setCellValue(i.getProducto().getNombre());
            }

            // 6. Metodo de Pago
            Sheet metodoSheet = workbook.createSheet("MetodosPago");
            createHeaderRow(metodoSheet, "ID", "Nombre");
            row = 1;
            for (MetodoPago m : metodoPagoRepository.findAll()) {
                Row r = metodoSheet.createRow(row++);
                r.createCell(0).setCellValue(m.getId_metodo());
                r.createCell(1).setCellValue(m.getNombre());
            }

            // 7. Estado Venta
            Sheet estadoSheet = workbook.createSheet("EstadosVenta");
            createHeaderRow(estadoSheet, "ID", "Nombre");
            row = 1;
            for (EstadoVenta e : estadoVentaRepository.findAll()) {
                Row r = estadoSheet.createRow(row++);
                r.createCell(0).setCellValue(e.getId_estado());
                r.createCell(1).setCellValue(e.getNombre());
            }

            // 8. Ventas
            Sheet ventasSheet = workbook.createSheet("Ventas");
            createHeaderRow(ventasSheet, "ID", "Fecha", "Total", "Cliente", "Metodo Pago", "Estado");
            row = 1;
            for (Venta v : ventaRepository.findAll()) {
                Row r = ventasSheet.createRow(row++);
                r.createCell(0).setCellValue(v.getId_venta());
                r.createCell(1).setCellValue(v.getFecha_venta().toString());
                r.createCell(2).setCellValue(v.getTotal());
                r.createCell(3).setCellValue(v.getCliente() != null ? v.getCliente().getNombre() : "N/A");
                r.createCell(4).setCellValue(v.getMetodoPago().getNombre());
                r.createCell(5).setCellValue(v.getEstadoVenta().getNombre());
            }

            // 9. Detalle Venta
            Sheet detalleSheet = workbook.createSheet("DetalleVenta");
            createHeaderRow(detalleSheet, "ID", "Venta", "Producto", "Cantidad", "Precio Unitario", "Subtotal");
            row = 1;
            for (DetalleVenta d : detalleVentaRepository.findAll()) {
                Row r = detalleSheet.createRow(row++);
                r.createCell(0).setCellValue(d.getId_detalle());
                r.createCell(1).setCellValue(d.getVenta().getId_venta());
                r.createCell(2).setCellValue(d.getProducto().getNombre());
                r.createCell(3).setCellValue(d.getCantidad());
                r.createCell(4).setCellValue(d.getPrecio_unitario());
                r.createCell(5).setCellValue(d.getSubtotal());
            }

            // Exportar a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            return bos.toByteArray();
        }
    }

    private void createHeaderRow(Sheet sheet, String... headers) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
        }
    }



     
}
