package com.model;

import com.musicbox.util.WorkingAreaType;
import com.musicbox.util.database.entities.MusicRoom;
import com.musicbox.util.database.entities.WorkingArea;
import javafx.beans.property.*;

/**
 * Created by Roy on 16.12.13.
 */
public class WorkingAreaModel {
    private WorkingArea workingArea;

    private ObjectProperty<MusicRoom> musicRoom = new SimpleObjectProperty<MusicRoom>();
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty tempo = new SimpleIntegerProperty();
    private StringProperty owner = new SimpleStringProperty();
    private ObjectProperty<WorkingAreaType> workingAreaType = new SimpleObjectProperty<WorkingAreaType>();
    private FloatProperty beat = new SimpleFloatProperty();
    private LongProperty length = new SimpleLongProperty();

    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public final int getTempo() {
        return tempo.get();
    }

    public final void setTempo(int tempo) {
        this.tempo.set(tempo);
    }

    public IntegerProperty tempoProperty() {
        return tempo;
    }

    public final float getBeat() {
        return this.beat.get();
    }

    public final void setBeat(float beat) {
        this.beat.set(beat);
    }

    public FloatProperty beatProperty() {
        return beat;
    }

    public final Long getLength() {
        return length.get();
    }

    public final void setLength(Long length) {
        this.length.set(length);
    }

    public LongProperty lengthProperty() {
        return length;
    }

    public WorkingAreaModel(int id, MusicRoom musicRoom, String name, int tempo, String owner, WorkingAreaType workingAreaType, float beat, Long length) {
        workingArea = new WorkingArea(id, musicRoom, name, tempo, owner, workingAreaType, beat, length);

        this.setName(name);
        this.setBeat(beat);
        this.setLength(length);
        this.setTempo(tempo);
    }
}
