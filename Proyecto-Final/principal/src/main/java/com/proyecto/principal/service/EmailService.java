package com.proyecto.principal.service;
import com.proyecto.principal.entity.CompraProducto;
import com.proyecto.principal.entity.ProductoCarrito;
import com.proyecto.principal.entity.VentaProducto;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmailWithProducts(String to, String subject, List<CompraProducto> productos) {
        String body = generateHtmlContent(productos);
        sendEmail(to, subject, body);
    }

    public void sendEmailSaleWithProducts(String email, String compraRealizada, List<VentaProducto> ventaProductos) {
        List<CompraProducto> productos = new ArrayList<>(List.of());
        ventaProductos.forEach(
                vp -> productos.add(new CompraProducto(null, vp.getProducto(), vp.getCantidad()))
        );
        String body = generateHtmlContent(productos);
        sendEmail(email, compraRealizada, body);
    }

    private String generateHtmlContent(List<CompraProducto> productos) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html>")
                .append("<body>")
                .append("<h1>Lista de Productos Vendidos</h1>")
                .append("<table border='1'>")
                .append("<tr>")
                .append("<th>Nombre</th>")
                .append("<th>Precio</th>")
                .append("<th>Cantidad</th>")
                .append("</tr>");

        for (CompraProducto producto : productos) {
            htmlContent.append("<tr>")
                    .append("<td>").append(producto.getProducto().getNombre()).append("</td>")
                    .append("<td>").append(producto.getProducto().getPrecio()).append("</td>")
                    .append("<td>").append(producto.getCantidad()).append("</td>")  // Asumimos que el campo stock es la cantidad vendida
                    .append("</tr>");
        }

        htmlContent.append("</table>")
                .append("</body>")
                .append("</html>");

        return htmlContent.toString();
    }
}