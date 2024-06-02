package com.proyecto.interfaz.negocio.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private void showClientes() {
        showWindow("view/ClienteView.fxml", "Gestión de Clientes");
    }

    @FXML
    private void showProductos() {
        showWindow("view/ProductoView.fxml", "Gestión de Productos");
    }

    @FXML
    private void showCategorias() {
        showWindow("view/CategoriaView.fxml", "Gestión de Categorias");
    }

    @FXML
    private void showProveedores() {
        showWindow("view/ProveedorView.fxml", "Gestión de Proveedores");
    }

    @FXML
    private void showCompraProducto() {
        showWindow("view/CompraProductoView.fxml", "Compra de Productos");
    }

    @FXML
    private void showReportes() {
        showWindow("view/ReporteView.fxml", "Reporte de Ventas");
    }

    @FXML
    private void showVentas() {
        showWindow("view/VentaView.fxml", "Gestión de Ventas");
    }

    private void showWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/proyecto/interfaz/negocio/" + fxmlFile));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
