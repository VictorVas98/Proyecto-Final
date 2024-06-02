package com.proyecto.interfaz.negocio.controller;

import com.google.gson.Gson;
import com.proyecto.interfaz.negocio.model.Categoria;
import com.proyecto.interfaz.negocio.model.Producto;
import com.proyecto.interfaz.negocio.model.Proveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoController {

    @FXML
    private TableView<Producto> productoTable;
    @FXML
    private TableColumn<Producto, String> nombreColumn;
    @FXML
    private TableColumn<Producto, Double> precioColumn;
    @FXML
    private TableColumn<Producto, Integer> stockColumn;
    @FXML
    private TableColumn<Producto, Proveedor> proveedorColumn;
    @FXML
    private TableColumn<Producto, Categoria> categoriaColumn;

    @FXML
    private TextField nombreField;
    @FXML
    private TextField precioField;
    @FXML
    private TextField stockField;
    @FXML
    private ComboBox<Proveedor> proveedorComboBox;
    @FXML
    private ComboBox<Categoria> categoriaComboBox;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();
    private final String apiUrl = "http://localhost:8080";

    @FXML
    private void initialize() {
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        proveedorColumn.setCellValueFactory(new PropertyValueFactory<>("proveedorNombre"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoriaNombre"));

        cargarProductos();
        cargarProveedores();
        cargarCategorias();
        setupComboBoxConverters();

        productoTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nombreField.setText(newValue.getNombre());
                precioField.setText(String.valueOf(newValue.getPrecio()));
                stockField.setText(String.valueOf(newValue.getStock()));
                proveedorComboBox.setValue(newValue.getProveedor());
                categoriaComboBox.setValue(newValue.getCategoria());
            }
        });
    }

    private void setupComboBoxConverters() {
        categoriaComboBox.setConverter(new StringConverter<Categoria>() {
            @Override
            public String toString(Categoria categoria) {
                return categoria != null ? categoria.getNombre() : "";
            }

            @Override
            public Categoria fromString(String string) {
                return categoriaComboBox.getItems().stream()
                        .filter(cat -> cat.getNombre().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        proveedorComboBox.setConverter(new StringConverter<Proveedor>() {
            @Override
            public String toString(Proveedor proveedor) {
                return proveedor != null ? proveedor.getNombre() : "";
            }

            @Override
            public Proveedor fromString(String string) {
                return proveedorComboBox.getItems().stream()
                        .filter(prov -> prov.getNombre().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    @FXML
    private void handleAgregar() {
        // Lógica para agregar producto
        try {
            Producto producto = new Producto();
            producto.setNombre(nombreField.getText());
            producto.setPrecio(Double.parseDouble(precioField.getText()));
            producto.setStock(Integer.parseInt(stockField.getText()));
            producto.setProveedor(proveedorComboBox.getValue());
            producto.setCategoria(categoriaComboBox.getValue());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl + "/productos"))
                    .setHeader("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(producto)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                cargarProductos();
                limpiarCampos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleActualizar() {
        // Lógica para actualizar producto
        try {
            Producto producto = productoTable.getSelectionModel().getSelectedItem();
            producto.setNombre(nombreField.getText());
            producto.setPrecio(Double.parseDouble(precioField.getText()));
            producto.setStock(Integer.parseInt(stockField.getText()));
            producto.setProveedor(proveedorComboBox.getValue());
            producto.setCategoria(categoriaComboBox.getValue());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl + "/productos/" + producto.getId()))
                    .setHeader("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(producto)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                cargarProductos();
                limpiarCampos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEliminar() {
        // Lógica para eliminar producto
        try {
            Producto producto = productoTable.getSelectionModel().getSelectedItem();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl + "/productos/" + producto.getId()))
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                cargarProductos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarProductos() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(apiUrl + "/productos")).GET().build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                List<Producto> productos = (List<Producto>) gson.fromJson(response.body(), List.class).stream().map(obj -> gson.fromJson(gson.toJson(obj), Producto.class)).collect(Collectors.toList());
                productoTable.getItems().setAll(productos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarProveedores() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(apiUrl + "/proveedores")).GET().build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            List<Proveedor> proveedores = (List<Proveedor>) gson.fromJson(response.body(), List.class).stream().map(obj -> gson.fromJson(gson.toJson(obj), Proveedor.class)).collect(Collectors.toList());
            ObservableList<Proveedor> proveedorList = FXCollections.observableArrayList(proveedores);
            proveedorComboBox.setItems(proveedorList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarCategorias() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(apiUrl + "/categorias")).GET().build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            List<Categoria> categorias = (List<Categoria>) gson.fromJson(response.body(), List.class).stream().map(obj -> gson.fromJson(gson.toJson(obj), Categoria.class)).collect(Collectors.toList());
            ObservableList<Categoria> categoriaList = FXCollections.observableArrayList(categorias);
            categoriaComboBox.setItems(categoriaList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        nombreField.clear();
        precioField.clear();
        stockField.clear();
        proveedorComboBox.getSelectionModel().clearSelection();
        categoriaComboBox.getSelectionModel().clearSelection();
    }
}
