package com.proyecto.interfaz.negocio.controller;

import com.google.gson.Gson;
import com.proyecto.interfaz.negocio.model.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class CategoriaController {

    @FXML
    private TableView<Categoria> categoriaTable;
    @FXML
    private TableColumn<Categoria, Long> idColumn;
    @FXML
    private TableColumn<Categoria, String> nombreColumn;

    @FXML
    private TextField nombreField;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();
    private final String apiUrl = "http://localhost:8080";

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        cargarCategorias();
    }

    @FXML
    private void handleAgregar() {
        String nombre = nombreField.getText();
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre(nombre);

        try {
            String requestBody = gson.toJson(nuevaCategoria);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl + "/categorias"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                cargarCategorias();
                nombreField.clear();
                mostrarMensaje("Categoría agregada correctamente");
            } else {
                mostrarError("Error al agregar categoría");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al agregar categoría");
        }
    }

    @FXML
    private void handleEliminar() {
        Categoria seleccionada = categoriaTable.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(apiUrl + "/categorias/" + seleccionada.getId()))
                        .DELETE()
                        .build();

                HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    cargarCategorias();
                    mostrarMensaje("Categoría eliminada correctamente");
                } else {
                    mostrarError("Error al eliminar categoría");
                }
            } catch (Exception e) {
                e.printStackTrace();
                mostrarError("Error al eliminar categoría");
            }
        } else {
            mostrarError("Debe seleccionar una categoría para eliminar");
        }
    }

    private void cargarCategorias() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl + "/categorias"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                List<Categoria> categorias = (List<Categoria>) gson.fromJson(response.body(), List.class).stream()
                        .map(obj -> gson.fromJson(gson.toJson(obj), Categoria.class))
                        .collect(Collectors.toList());
                ObservableList<Categoria> categoriaList = FXCollections.observableArrayList(categorias);
                categoriaTable.setItems(categoriaList);
            } else {
                mostrarError("Error al cargar categorías");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al cargar categorías");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
