package com.util;

import com.util.AudioSetup;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rfochtmann on 13.12.13.
 */
public class InputLoader {
    private Mixer mixer;
    private Mixer.Info[] aInfos;
    //private List<String> inputsList;
    private List<InputDevice> inputsList;

    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    ByteArrayOutputStream out;
    protected boolean running;
    private int recordNumber = 0;

    File wavFile = new File("../Record.wav");

    public InputLoader() {
        aInfos = null;
    }

    public ArrayList<InputDevice> loadInputs() {
        //inputsList = new ArrayList<String>();
        inputsList = new ArrayList<InputDevice>();
        aInfos = AudioSystem.getMixerInfo();

        /*for (Mixer.Info aInfo : aInfos) {

            if(aInfo.getDescription().contains("Capture")) {
                System.out.println(aInfo.getName() + ". Description: " + aInfo.getDescription());

            }
            inputsList.add(aInfo.getName());

        }*/

        for(int i=0; i<aInfos.length; i++) {
            //Only Devices from type "Audio Capture" are going to be added to the devices list
            if(aInfos[i].getDescription().contains("Capture")) {
                System.out.println(aInfos[i].getName() + ". Description: " + aInfos[i].getDescription());
                InputDevice inputDevice = new InputDevice(i, aInfos[i].getName());
                inputsList.add(inputDevice);
            }
        }

        return (ArrayList<InputDevice>) inputsList;
    }

    public void setupInput(int i) {
        mixer = AudioSystem.getMixer(aInfos[i]);
        System.out.println("Configured input: " + aInfos[i].getName());
    }

    public void record(){

        try {
            //Format Setup
            final AudioFormat format = AudioSetup.getFormat();

            DataLine.Info info = new DataLine.Info(
                    TargetDataLine.class, format);
            final TargetDataLine line = (TargetDataLine) mixer.getLine(info);

            //now open line to start recording
            line.open(format);
            line.start();
            //save audio in buffer
            Runnable runner = new Runnable() {
                int bufferSize = (int)format.getSampleRate()
                        * format.getFrameSize();
                byte buffer[] = new byte[bufferSize];

                public void run() {
                    out = new ByteArrayOutputStream();
                    running = true;
                    try {
                        System.out.println("Start recording...");
                        while (running) {
                            int count =
                                    line.read(buffer, 0, buffer.length);
                            if (count > 0) {
                                out.write(buffer, 0, count);
                            }
                        }
                        System.out.println("Stop recording");
                        line.close();
                        out.close();
                    } catch (IOException e) {
                        System.err.println("I/O problems: " + e);
                        //System.exit(-1);
                    }
                }
            };
            Thread captureThread = new Thread(runner);
            captureThread.start();
        } catch (LineUnavailableException e) {
            System.err.println("Line unavailable: " + e);
            //System.exit(-2);
        }
    }

    public float stop() {
        running = false;

        // save file
        byte audio[] = out.toByteArray();
        InputStream input =
                new ByteArrayInputStream(audio);
        final AudioFormat format = AudioSetup.getFormat();
        final AudioInputStream ais =
                new AudioInputStream(input, format,
                        audio.length / format.getFrameSize());
        float songLength = (float) (audio.length / format.getFrameSize())/50;
        //try to save file
        try {AudioSystem.write(ais, fileType, new File("../record" + recordNumber++ + ".wav"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return songLength;
    }
}
