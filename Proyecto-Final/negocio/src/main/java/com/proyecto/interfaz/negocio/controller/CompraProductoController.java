package com.proyecto.interfaz.negocio.controller;

import com.google.gson.Gson;
import com.proyecto.interfaz.negocio.model.CompraProducto;
import com.proyecto.interfaz.negocio.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CompraProductoController {

    @FXML
    private ComboBox<Producto> productoComboBox;
    @FXML
    private TextField cantidadField;
    @FXML
    private TableView<CompraProducto> compraProductoTable;
    @FXML
    private TableColumn<CompraProducto, String> productoColumn;
    @FXML
    private TableColumn<CompraProducto, Integer> cantidadColumn;

    private ObservableList<CompraProducto> compraProductoData = FXCollections.observableArrayList();
    private HttpClient httpClient;
    private Gson gson;

    public CompraProductoController() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    @FXML
    private void initialize() {
        productoColumn.setCellValueFactory(new PropertyValueFactory<>("producto"));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        productoComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Producto producto) {
                return producto != null ? producto.getNombre() : "";
            }

            @Override
            public Producto fromString(String string) {
                return null; // Not needed
            }
        });

        cargarProductos();
        cargarCompraProductos();
    }

    private void cargarProductos() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/productos"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            List<Producto> productos = gson.fromJson(response.body(), List.class);
            productoComboBox.getItems().setAll(productos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarCompraProductos() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/compra-productos"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            List<CompraProducto> compraProductos = gson.fromJson(response.body(), List.class);
            compraProductoTable.getItems().setAll(compraProductos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAgregar(ActionEvent event) {
        CompraProducto compraProducto = new CompraProducto();
        compraProducto.setProducto(productoComboBox.getValue());
        compraProducto.setCantidad(Integer.parseInt(cantidadField.getText()));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/compra-productos"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(compraProducto)))
                .build();

        try {
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            compraProductoData.add(compraProducto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleActualizar(ActionEvent event) {
        CompraProducto selectedCompra = compraProductoTable.getSelectionModel().getSelectedItem();
        if (selectedCompra != null) {
            selectedCompra.setProducto(productoComboBox.getValue());
            selectedCompra.setCantidad(Integer.parseInt(cantidadField.getText()));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/compra-productos/" + selectedCompra.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(selectedCompra)))
                    .build();

            try {
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                compraProductoTable.refresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        CompraProducto selectedCompra = compraProductoTable.getSelectionModel().getSelectedItem();
        if (selectedCompra != null) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/compra-productos/" + selectedCompra.getId()))
                    .DELETE()
                    .build();

            try {
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                compraProductoData.remove(selectedCompra);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
