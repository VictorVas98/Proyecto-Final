package com.proyecto.interfaz.negocio.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proyecto.interfaz.negocio.model.Proveedor;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class ProveedorController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField direccionField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField emailField;
    @FXML
    private TableView<Proveedor> proveedorTable;
    @FXML
    private TableColumn<Proveedor, String> colNombre;
    @FXML
    private TableColumn<Proveedor, String> colDireccion;
    @FXML
    private TableColumn<Proveedor, String> colTelefono;
    @FXML
    private TableColumn<Proveedor, String> colEmail;
    @FXML
    private TableColumn<Proveedor, Void> colAcciones;

    private final Gson gson = new Gson();
    private final String API_URL = "http://localhost:8080/proveedores";

    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colAcciones.setCellFactory(param -> new TableCell<Proveedor, Void>() {
            private final Button deleteButton = new Button("Eliminar");
            private final Button editButton = new Button("Editar");

            {
                deleteButton.setOnAction(event -> {
                    Proveedor proveedor = getTableView().getItems().get(getIndex());
                    handleEliminar(proveedor);
                });
                editButton.setOnAction(event -> {
                    Proveedor proveedor = getTableView().getItems().get(getIndex());
                    handleEditar(proveedor);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox pane = new HBox(editButton, deleteButton);
                    setGraphic(pane);
                }
            }
        });

        cargarProveedores();
    }

    private void cargarProveedores() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == 200) {
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder inline = new StringBuilder();
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();

                Type listType = new TypeToken<List<Proveedor>>() {}.getType();
                List<Proveedor> proveedores = gson.fromJson(inline.toString(), listType);
                proveedorTable.getItems().setAll(proveedores);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAgregar() {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(nombreField.getText());
        proveedor.setDireccion(direccionField.getText());
        proveedor.setTelefono(telefonoField.getText());
        proveedor.setEmail(emailField.getText());

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.getOutputStream().write(gson.toJson(proveedor).getBytes());
            conn.getOutputStream().flush();
            conn.getOutputStream().close();

            if (conn.getResponseCode() == 200) {
                cargarProveedores();
                nombreField.clear();
                direccionField.clear();
                telefonoField.clear();
                emailField.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEditar(Proveedor proveedor) {
        nombreField.setText(proveedor.getNombre());
        direccionField.setText(proveedor.getDireccion());
        telefonoField.setText(proveedor.getTelefono());
        emailField.setText(proveedor.getEmail());

        Button actualizarButton = new Button("Actualizar");
        actualizarButton.setOnAction(event -> handleActualizar(proveedor));

        GridPane pane = (GridPane) nombreField.getParent();
        pane.add(actualizarButton, 5, 0);
    }

    private void handleActualizar(Proveedor proveedor) {
        proveedor.setNombre(nombreField.getText());
        proveedor.setDireccion(direccionField.getText());
        proveedor.setTelefono(telefonoField.getText());
        proveedor.setEmail(emailField.getText());

        try {
            URL url = new URL(API_URL + "/" + proveedor.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.getOutputStream().write(gson.toJson(proveedor).getBytes());
            conn.getOutputStream().flush();
            conn.getOutputStream().close();

            if (conn.getResponseCode() == 200) {
                cargarProveedores();
                nombreField.clear();
                direccionField.clear();
                telefonoField.clear();
                emailField.clear();
                GridPane pane = (GridPane) nombreField.getParent();
                pane.getChildren().remove(5);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEliminar(Proveedor proveedor) {
        try {
            URL url = new URL(API_URL + "/" + proveedor.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.connect();

            if (conn.getResponseCode() == 200) {
                cargarProveedores();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
