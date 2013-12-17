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

    public WorkingAreaModel(int id, MusicRoom musicRoom, String name, int tempo, String owner, WorkingAreaType workingAreaType, float beat, Long length) {
        workingArea = new WorkingArea(id, musicRoom, name, tempo, owner, workingAreaType, beat, length);
    }
}
