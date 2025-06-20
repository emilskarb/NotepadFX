package com.example.notepadfx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Optional;

public class HelloController {

    @FXML
    private ListView<String> notesListView;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private Button saveButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private final HashMap<String, String> notes = new HashMap<>();
    private String currentNoteName = null;
    private String originalContent = "";

    @FXML
    public void initialize() {
        saveButton.setDisable(true);
        noteTextArea.textProperty().addListener((obs, oldVal, newVal) -> updateSaveButtonState());

        saveButton.setOnAction(e -> handleSave());
        editButton.setOnAction(e -> handleEdit());
        deleteButton.setOnAction(e -> handleDelete());
    }

    private void updateSaveButtonState() {
        String currentText = noteTextArea.getText();

        if (currentNoteName != null) {
            saveButton.setDisable(currentText.equals(originalContent));
        } else {
            saveButton.setDisable(currentText.trim().isEmpty());
        }
    }

    @FXML
    private void handleSave() {
        if (currentNoteName == null) {
            showSaveDialog();
        } else {
            String newContent = noteTextArea.getText();
            notes.put(currentNoteName, newContent);
            closeEditor(true);
        }
    }

    private void showSaveDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Zapisz notatkę");

        ButtonType confirmButtonType = new ButtonType("Potwierdź", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);

        TextField nameField = new TextField();
        nameField.setPromptText("Nazwa notatki");

        VBox content = new VBox(10);
        content.getChildren().add(new Label("Podaj nazwę notatki:"));
        content.getChildren().add(nameField);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return nameField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (name.trim().isEmpty()) {
                showAlert("Błąd", "Nie podano nazwy pliku.");
            } else if (notes.containsKey(name)) {
                showAlert("Błąd", "Notatka o tej nazwie już istnieje.");
            } else {
                notes.put(name, noteTextArea.getText());
                notesListView.getItems().add(name);
                noteTextArea.clear();
                updateSaveButtonState();
            }
        });
    }

    @FXML
    private void handleEdit() {
        String selected = notesListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (currentNoteName != null) {
            // Zapytaj o zapisanie zmian
            if (!noteTextArea.getText().equals(originalContent)) {
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Zapisz zmiany?");
                confirmDialog.setHeaderText("Czy chcesz zapisać zmiany?");
                confirmDialog.getButtonTypes().setAll(
                        new ButtonType("Zapisz", ButtonBar.ButtonData.YES),
                        new ButtonType("Nie zapisuj", ButtonBar.ButtonData.NO),
                        new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE)
                );

                Optional<ButtonType> result = confirmDialog.showAndWait();
                if (result.isPresent()) {
                    ButtonType choice = result.get();
                    if (choice.getButtonData() == ButtonBar.ButtonData.YES) {
                        notes.put(currentNoteName, noteTextArea.getText());
                        closeEditor(true);
                    } else if (choice.getButtonData() == ButtonBar.ButtonData.NO) {
                        closeEditor(true);
                    } else {
                        return;
                    }
                }
            } else {
                closeEditor(true);
            }
        }

        // Rozpocznij edycję nowej notatki
        currentNoteName = selected;
        originalContent = notes.get(selected);
        noteTextArea.setText(originalContent);
        editButton.setText("Zamknij");
        updateSaveButtonState();
    }

    private void handleDelete() {
        String selected = notesListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        notes.remove(selected);
        notesListView.getItems().remove(selected);

        if (selected.equals(currentNoteName)) {
            closeEditor(true);
        }
    }

    private void closeEditor(boolean clearTextArea) {
        if (clearTextArea) noteTextArea.clear();
        currentNoteName = null;
        originalContent = "";
        editButton.setText("Edytuj");
        updateSaveButtonState();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

