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

    private StringProperty name = new SimpleStringProperty();
    private ObjectProperty instrument = new SimpleObjectProperty<Instrument>();
    private StringProperty owner = new SimpleStringProperty();
    private StringProperty audioPath = new SimpleStringProperty();
    private LongProperty length = new SimpleLongProperty();
    private ObjectProperty musicRoom = new SimpleObjectProperty<MusicRoom>();

    public MusicSegmentModel(int id, String name, Instrument instrument, String owner, String audioPath, Long length, MusicRoom musicRoom) {
        musicSegment = new MusicSegment(id, name, instrument, owner, audioPath, length, musicRoom);
    }
}
