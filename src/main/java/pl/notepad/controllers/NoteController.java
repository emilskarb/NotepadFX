package pl.notepad.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.notepad.mocks.MockNotepadDatabase;
import pl.notepad.models.Note;
import java.util.Optional;
import static java.time.LocalDateTime.now;


public class NoteController {

    @FXML
    private TextField noteTitle;

    @FXML
    private TextArea noteTextArea;

    @FXML
    private ListView<Note> notesListView;

    @FXML
    public void initialize () {
        notesListView.setItems(MockNotepadDatabase.getAllNotes());
        notesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldNote, newNote) -> {
            if (newNote != null) notesSelected(newNote);
            });
    }

    private void notesSelected(Note newNote) {
        noteTitle.setText(newNote.getName());
        noteTextArea.setText(newNote.getContent());
    }

    public void addNewNote(ActionEvent actionEvent) {
        noteTitle.setText("Nowa notatka");
        noteTextArea.setText("");
        notesListView.getSelectionModel().clearSelection();
        noteTextArea.requestFocus();
    }

    public void deleteNote(ActionEvent actionEvent) {
        Note selectedNote = notesListView.getSelectionModel().getSelectedItem();
        if (selectedNote != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText(null);
            alert.setContentText("Na pewno checsz usunąć notatkę: " + selectedNote.getName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                MockNotepadDatabase.deleteNote(selectedNote);
            }
        }
        noteTitle.setText("");
        noteTextArea.setText("");
    }

    public void saveNote(ActionEvent actionEvent) {
        if (noteTitle.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uwaga");
            alert.setHeaderText(null);
            alert.setContentText("Tytuł notatki nie może być pusty!");
            alert.showAndWait();
            return;
        }
        Note selectedNote = notesListView.getSelectionModel().getSelectedItem();
        if (selectedNote != null){
            MockNotepadDatabase.editNote(selectedNote.getId(), noteTitle.getText(), noteTextArea.getText());
        } else {
            Note newNote = new Note(0, noteTitle.getText(), noteTextArea.getText(), now());
            MockNotepadDatabase.addNote(newNote);
        }
        noteTitle.setText("");
        noteTextArea.setText("");
        notesListView.refresh();
    }



}