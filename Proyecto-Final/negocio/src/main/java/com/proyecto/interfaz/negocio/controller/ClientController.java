package com.proyecto.interfaz.negocio.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.proyecto.interfaz.negocio.model.Cliente;
import javafx.beans.property.StringProperty;
import javafx.beans.property.LongProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ClientController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField emailField;

    @FXML
    private DatePicker fechaNacimientoField;

    @FXML
    private TableView<Cliente> clienteTable;

    @FXML
    private TableColumn<Cliente, String> nombreColumn;

    @FXML
    private TableColumn<Cliente, String> emailColumn;

    @FXML
    private TableColumn<Cliente, String> fechaNacimientoColumn;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @FXML
    private void initialize() {
        // Configurar las columnas
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        fechaNacimientoColumn.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        clienteTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nombreField.setText(newValue.getNombre());
                emailField.setText(newValue.getEmail());
                LocalDate fechaNacimiento = LocalDate.parse(newValue.getFechaNacimiento());
                fechaNacimientoField.setValue(fechaNacimiento);
            }
        });

        // Cargar los clientes al inicializar
        cargarClientes();
    }

    @FXML
    private void handleAgregar() {
        Cliente nuevoCliente = new Cliente();
        String nombre = nombreField.getText();
        String email = emailField.getText();
        String fechaNacimiento = fechaNacimientoField.getValue().toString();
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setEmail(email);
        nuevoCliente.setFechaNacimiento(fechaNacimiento);

        String json = new Gson().toJson(nuevoCliente);

        // Enviar el cliente a la API
        enviarClienteAPI(json);

        // Agregar el cliente a la tabla
        clienteTable.getItems().add(nuevoCliente);
    }

    @FXML
    private void handleActualizar() {
        Cliente seleccionado = clienteTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setNombre(nombreField.getText());
            seleccionado.setEmail(emailField.getText());
            seleccionado.setFechaNacimiento(fechaNacimientoField.getValue().toString());

            // Actualizar el cliente en la API
            actualizarClienteAPI(seleccionado);

            // Refrescar la tabla
            clienteTable.refresh();
        } else {
            mostrarAlerta("No se ha seleccionado ningún cliente", "Por favor, seleccione un cliente para actualizar.");
        }
    }

    @FXML
    private void handleEliminar() {
        Cliente seleccionado = clienteTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            // Eliminar el cliente de la API
            eliminarClienteAPI(seleccionado);

            // Eliminar el cliente de la tabla
            clienteTable.getItems().remove(seleccionado);
        } else {
            mostrarAlerta("No se ha seleccionado ningún cliente", "Por favor, seleccione un cliente para eliminar.");
        }
    }

    private void cargarClientes() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/clientes"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Type listType = new TypeToken<List<Cliente>>() {}.getType();
                List<Cliente> clientes = new Gson().fromJson(response.body(), listType);
                clienteTable.getItems().setAll(clientes);
            } else {
                mostrarAlerta("Error al cargar clientes", "Código de respuesta: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviarClienteAPI(String cliente) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/clientes"))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(cliente, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 201) {
                mostrarAlerta("Error al agregar cliente", "Código de respuesta: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizarClienteAPI(Cliente cliente) {
        try {
            String clienteJson = new Gson().toJson(cliente);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/clientes/" + cliente.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(clienteJson, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                mostrarAlerta("Error al actualizar cliente", "Código de respuesta: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eliminarClienteAPI(Cliente cliente) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/clientes/" + cliente.getId()))
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 204) {
                mostrarAlerta("Error al eliminar cliente", "Código de respuesta: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
