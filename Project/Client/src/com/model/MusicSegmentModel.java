package com.model;

import com.musicbox.util.Instrument;
import com.musicbox.util.database.entities.MusicRoom;
import com.musicbox.util.database.entities.MusicSegment;
import javafx.beans.property.*;

/**
 * Created by Roy on 16.12.13.
 */
public class MusicSegmentModel {
    MusicSegment musicSegment;

    private int id;
    private StringProperty name = new SimpleStringProperty();
    private Instrument instrument;
    private String owner;
    private StringProperty audioPath = new SimpleStringProperty();
    private Long length;
    private MusicRoom musicRoom;

    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public final String getAudioPath() {
        return audioPath.get();
    }

    public final void setAudioPath(String audioPath) {
        this.audioPath.set(audioPath);
    }

    public StringProperty audioPathProperty() {
        return audioPath;
    }

    public Instrument getInstrument() {
        return this.instrument;
    }

    public String getOwner() {
        return this.owner;
    }

    public Long getLength() {
        return this.length;
    }

    public MusicRoom getMusicRoom() {
        return this.musicRoom;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MusicSegmentModel that = (MusicSegmentModel) o;

        if (id != that.id) return false;
        if (audioPath != null ? !audioPath.equals(that.audioPath) : that.audioPath != null) return false;
        if (instrument != that.instrument) return false;
        if (length != null ? !length.equals(that.length) : that.length != null) return false;
        if (musicRoom != null ? !musicRoom.equals(that.musicRoom) : that.musicRoom != null) return false;
        if (musicSegment != null ? !musicSegment.equals(that.musicSegment) : that.musicSegment != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = musicSegment != null ? musicSegment.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (instrument != null ? instrument.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (audioPath != null ? audioPath.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (musicRoom != null ? musicRoom.hashCode() : 0);
        return result;
    }

    public MusicSegmentModel(int id, String name, Instrument instrument, String owner, String audioPath, Long length, MusicRoom musicRoom) {
        musicSegment = new MusicSegment(id, name, instrument, owner, audioPath, length, musicRoom);
        this.setName(name);
        this.setAudioPath(audioPath);
        this.instrument = instrument;
        this.id = id;
        this.owner = owner;
        this.musicRoom = musicRoom;
    }
}
