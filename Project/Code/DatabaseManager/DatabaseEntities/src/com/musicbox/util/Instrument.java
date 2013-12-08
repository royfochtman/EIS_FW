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
    KEYBOARD {
        public String toString(){
            return "Keyboard";
        }
    },
    GUITAR {
        public String toString(){
            return "Guitar";
        }
    },
    ELECTRICGUITAR{
        public String toString(){
            return "Electric Guitar";
        }
    },
    BASSGUITAR{
        public String toString(){
            return "Bass Guitar";
        }
    },
    DRUMS {
        public String toString(){
            return "Drums";
        }
    },
    PIANO{
        public String toString(){
            return "Piano";
        }
    };

    public static ArrayList<String> getInstruments() {
        ArrayList<String> list = new ArrayList<>();
        list.add(KEYBOARD.toString());
        list.add(GUITAR.toString());
        list.add(ELECTRICGUITAR.toString());
        list.add(BASSGUITAR.toString());
        list.add(DRUMS.toString());
        list.add(PIANO.toString());

        return list;
    }
}
