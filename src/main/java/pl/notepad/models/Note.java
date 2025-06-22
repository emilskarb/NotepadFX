package pl.notepad.models;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty content;
    private ObjectProperty<LocalDateTime> editionDate;

    public Note(int id, String name, String content, LocalDateTime editionDate) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.content = new SimpleStringProperty(content);
        this.editionDate = new SimpleObjectProperty<>(editionDate);
    }

    public int getId() {return id.get(); }
    public void setId(int id) {this.id.set(id); }
    public IntegerProperty idProperty() {return id; }

    public String getName() {return name.get(); }
    public void setName(String name) {this.name.set(name); }
    public StringProperty nameProperty() {return name;}

    public ObjectProperty<LocalDateTime> getEditionDate() {return editionDate; }
    public void setEditionDate(ObjectProperty<LocalDateTime> editionDate) {this.editionDate = editionDate; }


    public String getContent() {return content.get(); }
    public void setContent(String content) {this.content.set(content); }
    public StringProperty contentProperty() {return content; }

    @Override
    public String toString() {
        return this.name.getValue() + "\n" + this.editionDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    public String toItem() {
        // TODO: Zamieniac entery na \n a nie biayly enter + rozdzielac elementy innym znakiem niz srednik
        return this.id.get() + "$$$" + this.name.getValue() + "$$$" + this.content.getValue() + "$$$" + this.editionDate.getValue().toString();
    }

}
