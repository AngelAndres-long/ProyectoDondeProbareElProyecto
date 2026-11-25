module com.example.proyectodondeprobareelproyecto {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.proyectodondeprobareelproyecto to javafx.fxml;
    exports com.example.proyectodondeprobareelproyecto;
}