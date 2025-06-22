package pl.notepad.mocks;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.notepad.Application;
import pl.notepad.models.Note;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.time.LocalDateTime.now;

public class MockNotepadDatabase {

    private static final ObservableList<Note> notesList = getNotesFromFile();

    private static ObservableList<Note> getNotesFromFile() {

        ArrayList<Note> noteList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("files/notes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String content = parts[2];
                    String editionDate = parts[3];
                    noteList.add(new Note(id, name, content, LocalDateTime.parse(editionDate)));
                }
            }
            return FXCollections.observableArrayList(noteList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ObservableList<Note> getAllNotes() {
        return notesList;
    }

    public static void addNote(Note note) {
        if (notesList != null) {
            note.setId(getIncrement());
            notesList.add(note);
            saveToFile();
        }
    }

    private static int increment = 100;
    public static int getIncrement() {
        return increment++;
    }

    public static Note getById (int id) {
        Note searchNote = null;
        for (int i = 0;  i < notesList.size(); i++) {
            if(notesList.get(i).getId() == id) {
                searchNote = notesList.get(i);
            }
        }
        return searchNote;
    }

    public static void deleteNote(Note note) {
        if (notesList != null) {
            notesList.remove(note);
            saveToFile();
        }
    }

    public static void editNote(int id, String newName, String newContent) {
       Note editedNote = getById(id);
       if(editedNote != null) {
           editedNote.setName(newName);
           editedNote.setContent(newContent);
           editedNote.setEditionDate(new SimpleObjectProperty<>(now()));
           saveToFile();
       }
    }

    private static void saveToFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("files/notes.txt"))) {
            for (Note note : notesList) {
                writer.write(note.toItem());
                writer.newLine();
            }
            System.out.println("Zapisano " + notesList.size() + " notatek do pliku.");
        } catch (IOException e) {
            System.err.println("Błąd zapisu pliku: " + e.getMessage());
        }



    }

}
