module com.example.notatnikfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires jdk.compiler;

    opens pl.notepad to javafx.fxml;
    exports pl.notepad;
    exports pl.notepad.mocks;
    opens pl.notepad.mocks to javafx.fxml;
    exports pl.notepad.models;
    opens pl.notepad.models to javafx.fxml;
    exports pl.notepad.controllers;
    opens pl.notepad.controllers to javafx.fxml;
}