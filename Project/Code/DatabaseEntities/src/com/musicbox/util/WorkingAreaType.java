package com.musicbox.util;

import java.io.Serializable;

/**
 * @author David Wachs
 * A working-area can be public or private.
 */
public enum WorkingAreaType implements Serializable{
    /**
     * A working-area is private, if only the owner of the area can make changes on
     * working-area data.
     */
    PRIVATE ("private"),
    /**
     * A working-area is public, if all members of a music-room-session
     * can change working-area-data. For example add or remove tracks.
     */
    PUBLIC ("public");

    private final String workingAreaTypeString;

    private WorkingAreaType(final String workingAreaTypeString){
        this.workingAreaTypeString = workingAreaTypeString;
    }

    public static WorkingAreaType fromString(final String workingAreaTypeString) {
        if(workingAreaTypeString == null || workingAreaTypeString.isEmpty())
            return null;

        for(WorkingAreaType type : WorkingAreaType.values()) {
            if(workingAreaTypeString.equals(type.toString()))
                return type;
        }
        return null;
    }

    @Override
    public String toString() {
        return workingAreaTypeString;
    }
}
