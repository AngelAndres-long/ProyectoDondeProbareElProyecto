package com.example.proyectodondeprobareelproyecto.vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación.
 */
public class Aplicacion extends Application{

    @Override
    public void start(Stage stage){
        stage.setTitle("Sistema de Rutas - Grafos");
        stage.setScene(new Scene(new PantallaPrincipal(),1100,700));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}