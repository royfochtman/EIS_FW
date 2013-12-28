package com.musicbox.util.database.entities;

import com.musicbox.util.EntityClass;
import com.musicbox.util.Instrument;
import com.musicbox.util.globalobject.GlobalObject;

/**
 * An instance of this Class represents one tupel of MusicBox MySQL
 * database table <i>music_segment</i>. See database ERM-diagram for details.
 *
 * @author David Wachs
 */
public class MusicSegment extends GlobalObject {
    private String name;
    private Instrument instrument;
    private String owner;
    private String audioPath;
    private Long length;
    private MusicRoom musicRoom;

    public MusicSegment() {
        super.setEntityClass(EntityClass.MUSIC_SEGMENT_CLASS);
        name = "";
        instrument = null;
        owner = "";
        audioPath = "";
        length = 0L;
    }

    public MusicSegment(int id, String name, Instrument instrument, String owner, String audioPath, Long length, MusicRoom musicRoom) {
        super.setEntityClass(EntityClass.MUSIC_SEGMENT_CLASS);
        super.setId(id);
        setName(name);
        setInstrument(instrument);
        setOwner(owner);
        setAudioPath(audioPath);
        setLength(length);
        setMusicRoom(musicRoom);
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

    public MusicRoom getMusicRoom() {
        return this.musicRoom;
    }

    public void setMusicRoom(MusicRoom musicRoom) {
        this.musicRoom = musicRoom;
    }

    public boolean isValid() {
        if(getId() > 0 && name != null && !name.isEmpty() && instrument != null && owner != null && !owner.isEmpty()
                && !audioPath.isEmpty() && length != null && length >= 0L && musicRoom != null && musicRoom.isValid())
            return true;
        else
            return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        MusicSegment musicSegment = (MusicSegment) obj;

        return getId() == musicSegment.getId()
                && name != null && name.equals(musicSegment.getName())
                && instrument != null && instrument.equals(musicSegment.getInstrument())
                && owner != null && owner.equals(musicSegment.getOwner())
                && audioPath != null && audioPath.equals(musicSegment.getAudioPath())
                && length != null && length.equals(musicSegment.getLength())
                && musicRoom != null && musicRoom.equals(musicSegment.getMusicRoom());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (instrument != null ? instrument.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (audioPath != null ? audioPath.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (musicRoom != null ? musicRoom.hashCode() : 0);
        return result;
    }
}
