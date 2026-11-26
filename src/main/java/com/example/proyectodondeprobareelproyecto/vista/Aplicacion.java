package com.example.proyectodondeprobareelproyecto.vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación.
 */
public class Aplicacion extends Application{

    private PantallaPrincipal pantalla;

    @Override
    public void start(Stage stage){
        pantalla=new PantallaPrincipal();
        Scene scene=new Scene(pantalla,1200,750);
        stage.setTitle("Sistema de Rutas - Grafos");
        stage.setScene(scene);
        stage.setOnCloseRequest(e->pantalla.guardarAlSalir());
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}