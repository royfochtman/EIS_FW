package com.model;

import com.musicbox.util.Instrument;
import com.musicbox.util.database.entities.MusicRoom;
import com.musicbox.util.database.entities.MusicSegment;

/**
 * Created by Roy on 16.12.13.
 */
public class MusicSegmentModel {
    MusicSegment musicSegment;

    public MusicSegmentModel(int id, String name, Instrument instrument, String owner, String audioPath, Long length, MusicRoom musicRoom) {
        musicSegment = new MusicSegment(id, name, instrument, owner, audioPath, length, musicRoom);
    }
}
