module com.example.proyectodondeprobareelproyecto {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    exports com.example.proyectodondeprobareelproyecto.vista;
    opens com.example.proyectodondeprobareelproyecto.vista to javafx.fxml;
    requires javafx.media;

    opens com.example.proyectodondeprobareelproyecto to javafx.fxml;
    exports com.example.proyectodondeprobareelproyecto;
}