package pl.notepad;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("note-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1020, 640);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setMinWidth(600);
        stage.setMinHeight(500);
        stage.setTitle("Notatnik");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}