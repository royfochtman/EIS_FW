package com.model;

import com.musicbox.util.Instrument;
import com.musicbox.util.database.entities.Track;
import com.musicbox.util.database.entities.WorkingArea;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Roy on 16.12.13.
 */
public class TrackModel extends Track {

    Track track;

    /**
     * Databinding
     */
    private StringProperty name = new SimpleStringProperty();
    private ObjectProperty<Instrument> instrument = new SimpleObjectProperty<Instrument>();

    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObjectProperty instrumentProperty() {
        return instrument;
    }
    /*
    @Override
    public ObjectProperty getInstrument() {
        return this.instrument;
    }        */

    public void setInstrument(Instrument instrument) {
        this.instrument.set(instrument);
    }

    /**
     *
     * @param id
     * @param workingArea
     * @param instrument
     * @param volume
     * @param name
     * @param length
     */
    public TrackModel(int id, WorkingArea workingArea, Instrument instrument, int volume, String name, Long length) {
        track = new Track(id, workingArea, instrument, volume, name, length);

        this.nameProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                System.out.println("Track Name changed. Old Name:  " + s + ". New name: " + s2);
                track.setName(s2);
            }
        });

        this.instrumentProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o2) {
                System.out.println("Tracks Instrument Changed. Old Instrument: " + o.toString() + ". New Instrument: " + o2.toString());
                track.setInstrument((Instrument) o2);
            }
        });
    }

}
