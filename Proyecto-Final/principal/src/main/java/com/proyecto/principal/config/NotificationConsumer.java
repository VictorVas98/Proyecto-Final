package com.proyecto.principal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.principal.entity.Compra;
import com.proyecto.principal.entity.CompraProducto;
import com.proyecto.principal.entity.Venta;
import com.proyecto.principal.entity.VentaProducto;
import com.proyecto.principal.repository.VentaProductoRepository;
import com.proyecto.principal.service.EmailService;
import com.proyecto.principal.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationConsumer {

    @Autowired
    private VentaService ventaService;

    @KafkaListener(topics = "order_notifications", groupId = "group_id")
    public void consume(String message) {
        try {
            message = message.substring(message.indexOf("{"));
            System.out.println("Mensaje recibido: " + message);
            Venta compra = convertToVenta(message);
            ventaService.saveVenta(compra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Venta convertToVenta(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Venta.class);
    }

}