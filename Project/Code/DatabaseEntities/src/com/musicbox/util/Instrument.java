package com.musicbox.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
public enum Instrument implements Serializable {
    KEYBOARD ("Keyboard"),
    GUITAR ("Guitar"),
    ELECTRICGUITAR ("Electric Guitar"),
    BASSGUITAR ("Bass Guitar"),
    DRUMS ("Drums"),
    PIANO ("Piano");

    private final String instrumentString;

    private Instrument(final String instrument){
        this.instrumentString = instrument;
    }

    public static ArrayList<String> getInstruments() {
        ArrayList<String> list = new ArrayList<>();
        for(Instrument instrument : Instrument.values())
            list.add(instrument.toString());

        return list;
    }

    public static Instrument fromString(String instrumentString){
        if(instrumentString == null && instrumentString.isEmpty())
            return null;

        for(Instrument instrument : Instrument.values()){
            if(instrumentString.equals(instrument.toString()))
                return instrument;
        }

        return null;
    }

    @Override
    public String toString() {
        return instrumentString;
    }
}
