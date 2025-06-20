module com.example.notepadfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.notepadfx to javafx.fxml;
    exports com.example.notepadfx;
}