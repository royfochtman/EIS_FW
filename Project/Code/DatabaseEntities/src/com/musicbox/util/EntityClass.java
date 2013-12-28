package com.musicbox.util;

import com.musicbox.util.database.entities.*;

/**
 * @author David Wachs
 */
public enum EntityClass {
    MUSIC_ROOM_CLASS(MusicRoom.class),
    WORKING_AREA_CLASS(WorkingArea.class),
    TRACK_CLASS(Track.class),
    VARIATION_CLASS(Variation.class),
    MUSIC_SEGMENT_CLASS(MusicSegment.class),
    VARIATION_TRACK_CLASS(VariationTrack.class),
    MUSIC_ROOM_DATA_CONTAINER_CLASS(MusicRoomDataContainer.class);

    private final Class c;

    private EntityClass(Class c){
        this.c = c;
    }
}
