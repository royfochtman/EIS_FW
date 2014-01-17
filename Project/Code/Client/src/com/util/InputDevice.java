package com.util;

/**
 * Created by Roy on 16.12.13.
 * InputDevice describes every input device, which can capture audio.
 */
public class InputDevice {
    private int portIndex;
    private String name;

    public InputDevice(int portIndex, String name) {
        this.setPortIndex(portIndex);
        this.setName(name);
    }

    public InputDevice() {

    }

    public int getPortIndex() {
        return portIndex;
    }

    public void setPortIndex(int portIndex) {
        this.portIndex = portIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
