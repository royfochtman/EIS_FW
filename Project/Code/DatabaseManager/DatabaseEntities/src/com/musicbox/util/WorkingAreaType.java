package com.musicbox.util;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
public enum WorkingAreaType implements Serializable{
    PRIVATE ("private"),
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
