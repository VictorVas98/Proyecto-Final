package com.proyecto.interfaz.negocio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/proyecto/interfaz/negocio/view/MenuView.fxml"));
			Scene scene = new Scene(loader.load());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Menú Principal");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch();
	}
}
