package com.util;

import com.util.AudioSetup;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.Mixer;

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
    private SourceDataLine lineOut;
    private TargetDataLine lineIn;
    private final AudioFormat format = AudioSetup.getFormat();
    private boolean outputSelected = false;
    public static String lastPath;

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

    public boolean setupInput(int i) {
        try {
            mixer = AudioSystem.getMixer(aInfos[i]);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            lineIn = (TargetDataLine) mixer.getLine(info);
            System.out.println("Configured input: " + aInfos[i].getName());
            return true;
        } catch (LineUnavailableException ex) {
            System.out.println("Input line unavailable: " + ex);
            return false;
        }
    }

    public ArrayList<InputDevice> loadOutputs() {
        ArrayList<InputDevice> outputsList = new ArrayList<InputDevice>();
        Mixer.Info[] aInfos = AudioSystem.getMixerInfo();
        for (int i= 0; i<aInfos.length; i++) {
            if(aInfos[i].getDescription().contains("Playback")) {
                System.out.println(aInfos[i].getName() + ". Description: " + aInfos[i].getDescription());
                InputDevice inputDevice = new InputDevice(i, aInfos[i].getName());
                outputsList.add(inputDevice);
            }
        }
        return outputsList;
    }

    public boolean setupOutput(int i) {
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            lineOut = (SourceDataLine) AudioSystem.getMixer(aInfos[i]).getLine(info);
            System.out.println("Configured output: " + aInfos[i].getName());
            outputSelected = true;
            return true;
        } catch (LineUnavailableException ex) {
            System.out.println("Output line unavailable: " + ex);
            outputSelected = false;
            return false;
        }
        /*if(!AudioSystem.isLineSupported(Port.Info.SPEAKER)) {
            System.out.println("Speaker not supported.");
        } else try{
            lineOut = (SourceDataLine) AudioSystem.getLine(Port.Info.SPEAKER);
            final AudioFormat format = AudioSetup.getFormat();
            lineOut.open(format);
            System.out.println("Output configured: " + lineOut.getLineInfo().toString());

        } catch (LineUnavailableException ex) {
            System.err.println("Line Out could not be opened: " + ex);
        } */
    }

    public void record(){
        try {
            lineIn.open(format);
            lineIn.start();

            //Start configured output
            if(outputSelected) {
                lineOut.open(format);
                lineOut.start();
            }

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
                        if(outputSelected) {
                            while(running) {
                                int count = lineIn.read(buffer, 0, buffer.length);
                                if (count > 0) {
                                    //Hier evtl multithreading!!!! könnte das problem der Latenz lösen
                                    out.write(buffer, 0, count);
                                    lineOut.write(buffer, 0, count);
                                }
                            }

                        } else {
                            while (running) {
                                int count =
                                        lineIn.read(buffer, 0, buffer.length);
                                if (count > 0) {
                                    out.write(buffer, 0, count);
                                }
                            }
                        }
                        lineIn.close();
                        out.close();
                        if(outputSelected) {
                            lineOut.drain();
                            lineOut.close();
                        }
                        System.out.println("Stop recording");
                    } catch (IOException e) {
                        System.err.println("I/O problems: " + e);
                    }
                }
            };
            Thread captureThread = new Thread(runner);
            captureThread.start();


        } catch (LineUnavailableException ex) {
            System.out.println("Line In could not be opened" + ex);
        }

    }



    public float stop() {
        running = false;

        // save file
        byte audio[] = out.toByteArray();
        InputStream input = new ByteArrayInputStream(audio);
        final AudioInputStream ais = new AudioInputStream(input, format, audio.length / format.getFrameSize());
        float songLength = (float) (audio.length / format.getFrameSize())/50;
        System.out.println("Song length: " + songLength);
        //try to save file
        try {
            lastPath =  "../record" + recordNumber++ + ".wav";
            AudioSystem.write(ais, fileType, new File(lastPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songLength;
    }
}
