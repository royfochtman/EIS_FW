package com.musicbox.util.database.entities;

import com.musicbox.util.Instrument;
import com.musicbox.util.globalobject.GlobalObject;
import com.sun.istack.internal.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class MusicSegment extends GlobalObject {
    private String name;
    private Instrument instrument;
    private String owner;
    private String audioPath;
    private Long length;

    public MusicSegment() {
        super.setDataClass(MusicSegment.class);
        name = "";
        instrument = null;
        owner = "";
        audioPath = "";
        length = 0L;
    }

    public MusicSegment(int id, String name, Instrument instrument, String owner, String audioPath, Long length) {
        super.setDataClass(MusicSegment.class);
        super.setId(id);
        setName(name);
        setInstrument(instrument);
        setOwner(owner);
        setAudioPath(audioPath);
        setLength(length);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null)
            name = "";

        this.name = name;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        if(owner == null)
            owner = "";

        this.owner = owner;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        if(audioPath == null)
            audioPath = "";

        this.audioPath = audioPath;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public boolean isValid() {
        if(!name.isEmpty() && instrument != null && !owner.isEmpty()
                && !audioPath.isEmpty() && length >= 0L)
            return true;
        else
            return false;
    }
}
