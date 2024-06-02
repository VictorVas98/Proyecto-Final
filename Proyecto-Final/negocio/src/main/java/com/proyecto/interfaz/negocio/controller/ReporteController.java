package com.proyecto.interfaz.negocio.controller;

import com.google.gson.Gson;
import com.proyecto.interfaz.negocio.model.Reporte;
import com.proyecto.interfaz.negocio.model.Venta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ReporteController {

    @FXML
    private TableView<Venta> reporteTable;
    @FXML
    private TableColumn<Reporte, Long> idColumn;
    @FXML
    private TableColumn<Reporte, String> clienteColumn;
    @FXML
    private TableColumn<Reporte, String> productoColumn;
    @FXML
    private TableColumn<Reporte, String> fechaColumn;
    @FXML
    private TableColumn<Reporte, String> estadoColumn;
    @FXML
    private TableColumn<Reporte, Double> totalVentaColumn;
    @FXML
    DatePicker filtroFechaInicio;
    @FXML
    DatePicker filtroFechaFin;
    @FXML
    private TextField filtroCliente;
    @FXML
    private TextField filtroProducto;
    @FXML
    private Label totalVentasLabel;
    @FXML
    private Label cantidadVentasLabel;

    private ObservableList<Venta> reporteData = FXCollections.observableArrayList();
    private final Gson gson = new Gson();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String apiUrl = "http://localhost:8080/reportes";

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clienteColumn.setCellValueFactory(new PropertyValueFactory<>("clienteNombre"));
        productoColumn.setCellValueFactory(new PropertyValueFactory<>("productos"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        totalVentaColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        reporteTable.setItems(reporteData);
    }

    @FXML
    private void reportePorFecha() {
        LocalDate fechaInicio = filtroFechaInicio.getValue();
        LocalDate fechaFin = filtroFechaFin.getValue();
        Reporte reporte = crearReportePorFecha(fechaInicio, fechaFin);
        assert reporte != null && reporte.getVentas() != null;
        actualizarTabla(reporte);
    }

    @FXML
    private void reportePorCliente() {
        String cliente = filtroCliente.getText();
        Reporte reporte = crearReportePorCliente(cliente);
        assert reporte != null && reporte.getVentas() != null;
        actualizarTabla(reporte);
    }

    @FXML
    private void reportePorProducto() {
        // Lógica para buscar reportes por producto y actualizar la tabla
        String producto = filtroProducto.getText();
        Reporte reporte = crearReportePorProducto(producto);
        assert reporte != null && reporte.getVentas() != null;
        actualizarTabla(reporte);
    }

    private Reporte crearReportePorFecha(LocalDate inicio, LocalDate fin) {
        try {
            String inicioStr = inicio.toString();
            String finStr = fin.toString();
            String url = apiUrl + "/tiempo?inicio=" + URLEncoder.encode(inicioStr, StandardCharsets.UTF_8) +
                    "&fin=" + URLEncoder.encode(finStr, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), Reporte.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Reporte crearReportePorCliente(String cliente) {
        try {
            String url = apiUrl + "/cliente/" + cliente;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), Reporte.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Reporte crearReportePorProducto(String producto) {
        try {
            String url = apiUrl + "/producto/" + producto;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), Reporte.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void actualizarTabla(Reporte reporte) {
        if (reporte == null || reporte.getVentas() == null || reporte.getVentas().isEmpty()) {
            reporteData.clear();
            totalVentasLabel.setText("0.0");
            cantidadVentasLabel.setText("0");
            return;
        }
        List<Venta> ventas = reporte.getVentas();
        Double totalVentas = reporte.getTotalVentas();
        int cantidadVentas = ventas.size();
        reporteData.setAll(ventas);

        totalVentasLabel.setText("" + totalVentas);
        cantidadVentasLabel.setText("" + cantidadVentas);
    }
}
