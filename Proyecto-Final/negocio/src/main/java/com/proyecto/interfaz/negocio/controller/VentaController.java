package com.proyecto.interfaz.negocio.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.proyecto.interfaz.negocio.model.Cliente;
import com.proyecto.interfaz.negocio.model.Producto;
import com.proyecto.interfaz.negocio.model.Venta;
import com.proyecto.interfaz.negocio.model.VentaProducto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VentaController {
    @FXML
    private TableView<VentaProducto> productoTable;
    @FXML
    private TableColumn<VentaProducto, Long> productoIdColumn;
    @FXML
    private TableColumn<VentaProducto, String> productoNombreColumn;
    @FXML
    private TableColumn<VentaProducto, Integer> cantidadColumn;
    @FXML
    private TableColumn<VentaProducto, Double> precioColumn;
    @FXML
    private TableColumn<VentaProducto, Double> subtotalColumn;
    @FXML
    private ComboBox<Cliente> clienteComboBox;
    @FXML
    private ComboBox<Producto> productoComboBox;
    @FXML
    private TextField cantidadField;
    @FXML
    private Button agregarProductoButton;
    @FXML
    private Button finalizarVentaButton;

    private ObservableList<VentaProducto> productoData = FXCollections.observableArrayList();
    private HttpClient client;
    private Gson gson;

    public VentaController() {
        this.client = HttpClient.newHttpClient();
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    }

    @FXML
    private void initialize() {
        productoIdColumn.setCellValueFactory(new PropertyValueFactory<>("productoId"));
        productoNombreColumn.setCellValueFactory(new PropertyValueFactory<>("productoNombre"));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        subtotalColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        agregarProductoButton.setOnAction(event -> agregarProducto());
        finalizarVentaButton.setOnAction(event -> finalizarVenta());

        cargarClientes();
        cargarProductos();
        setupComboBoxConverters();
    }

    private void cargarClientes() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/clientes"))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    List<Cliente> clientes = gson.fromJson(response, new TypeToken<List<Cliente>>() {
                    }.getType());
                    clienteComboBox.setItems(FXCollections.observableArrayList(clientes));
                });
    }

    private void cargarProductos() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/productos"))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    List<Producto> productos = gson.fromJson(response, new TypeToken<List<Producto>>() {
                    }.getType());
                    productoComboBox.setItems(FXCollections.observableArrayList(productos));
                });
    }

    private void setupComboBoxConverters() {
        clienteComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente != null ? cliente.getNombre() : "";
            }

            @Override
            public Cliente fromString(String string) {
                return clienteComboBox.getItems().stream()
                        .filter(cat -> cat.getNombre().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        productoComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Producto proveedor) {
                return proveedor != null ? proveedor.getNombre() : "";
            }

            @Override
            public Producto fromString(String string) {
                return productoComboBox.getItems().stream()
                        .filter(prov -> prov.getNombre().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    private void agregarProducto() {
        Producto producto = productoComboBox.getValue();
        int cantidad = Integer.parseInt(cantidadField.getText());

        if (producto == null || cantidad <= 0) {
            showAlert("Error", "Seleccione un producto y una cantidad válida");
            return;
        }
        for (VentaProducto vp : productoData) {
            if (vp.getProductoId().equals(producto.getId())) {
                vp.setCantidad(vp.getCantidad() + cantidad);
                productoTable.refresh();
                return;
            }
        }
        VentaProducto ventaProducto = new VentaProducto();
        ventaProducto.setProducto(producto);
        ventaProducto.setCantidad(cantidad);
        productoData.add(ventaProducto);
        productoTable.setItems(productoData);
    }

    private void finalizarVenta() {
        Venta venta = new Venta();
        venta.setCliente(clienteComboBox.getValue());
        venta.setFecha(new Date());
        venta.setEstado(Venta.EstadoVenta.DESPACHADO);
        venta.setVentaProductos(new ArrayList<>(productoData));

        String ventaJson = gson.toJson(venta);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/ventas"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(ventaJson))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        showAlert("Éxito", "Venta realizada correctamente");
                        productoData.clear();
                        productoTable.setItems(productoData);
                        limpiarCampos();
                    } else {
                        showAlert("Error", "Error al realizar la venta");
                    }
                });
    }

    private void limpiarCampos() {
        clienteComboBox.setValue(null);
        productoComboBox.setValue(null);
        cantidadField.clear();
        productoData.clear();
        productoTable.setItems(productoData);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
