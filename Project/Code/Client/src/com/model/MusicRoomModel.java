package com.model;

import com.musicbox.util.database.entities.MusicRoom;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Roy on 16.12.13.
 */
public class MusicRoomModel {
    private MusicRoom musicRoom;

    /**
     * Databinding
     */
    private StringProperty name = new SimpleStringProperty();

    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public MusicRoom getMusicRoom() {
        return this.musicRoom;
    }

    public MusicRoomModel(String name, int id) {
        musicRoom = new MusicRoom(id, name);

        this.nameProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                System.out.println("Musicrooms name changed, old: " + s + ", new: " + s2);
                musicRoom.setName(s2);
            }
        });
    }
}
