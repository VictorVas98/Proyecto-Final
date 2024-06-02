module com.proyecto.interfaz.negocio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.gson;
    requires java.net.http;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires kafka.clients;
    requires mysql.connector.java;
    requires com.fasterxml.jackson.annotation;

    opens com.proyecto.interfaz.negocio.model to com.google.gson, javafx.base;
    opens com.proyecto.interfaz.negocio to javafx.fxml;
    opens com.proyecto.interfaz.negocio.controller to javafx.fxml;
    opens com.proyecto.interfaz.negocio.view to javafx.fxml;

    exports com.proyecto.interfaz.negocio;
    exports com.proyecto.interfaz.negocio.controller;
}