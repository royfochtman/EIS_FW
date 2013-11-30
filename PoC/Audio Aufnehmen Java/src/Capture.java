/**
 * Created with IntelliJ IDEA.
 * User: Roy
 * Date: 08.11.13
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

public class Capture extends JFrame{

    protected boolean running;
    ByteArrayOutputStream out;

    // path of the wav file
    File wavFile = new File("E:/Dropbox/FH Köln/Medieninformatik/5. Sem/EIS/Eis/PoC/Audio Aufnehmen Java/Record.wav");

    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    public Capture() {
        super("Capture Sound Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container content = getContentPane();

        final JButton capture = new JButton("Capture");
        final JButton stop = new JButton("Stop");
        final JButton play = new JButton("Play");
        final JButton save = new JButton("Save");

        capture.setEnabled(true);
        stop.setEnabled(false);
        play.setEnabled(false);
        save.setEnabled(false);

        ActionListener captureListener =
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        capture.setEnabled(false);
                        stop.setEnabled(true);
                        play.setEnabled(false);
                        save.setEnabled(false);
                        captureAudio();
                    }
                };
        capture.addActionListener(captureListener);
        content.add(capture, BorderLayout.NORTH);

        ActionListener stopListener =
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        capture.setEnabled(true);
                        stop.setEnabled(false);
                        play.setEnabled(true);
                        save.setEnabled(true);
                        running = false;
                    }
                };
        stop.addActionListener(stopListener);
        content.add(stop, BorderLayout.CENTER);

        ActionListener playListener =
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        playAudio();
                    }
                };
        play.addActionListener(playListener);
        content.add(play, BorderLayout.SOUTH);

        ActionListener saveListener =
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveAudio();
                    }
                };
        save.addActionListener(saveListener);
        content.add(save, BorderLayout.WEST);
    }

    private void captureAudio() {
        try {
            //record audio from Line-Input (Mic)
            final AudioFormat format = getFormat();
            DataLine.Info info = new DataLine.Info(
                    TargetDataLine.class, format);
            final TargetDataLine line = (TargetDataLine)
                    AudioSystem.getLine(info);
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
                        while (running) {
                            int count =
                                    line.read(buffer, 0, buffer.length);
                            if (count > 0) {
                                out.write(buffer, 0, count);
                            }
                        }
                        out.close();
                    } catch (IOException e) {
                        System.err.println("I/O problems: " + e);
                        System.exit(-1);
                    }
                }
            };
            Thread captureThread = new Thread(runner);
            captureThread.start();
        } catch (LineUnavailableException e) {
            System.err.println("Line unavailable: " + e);
            System.exit(-2);
        }
    }

    private void playAudio() {
        try {
            byte audio[] = out.toByteArray();
            InputStream input =
                    new ByteArrayInputStream(audio);
            final AudioFormat format = getFormat();
            final AudioInputStream ais =
                    new AudioInputStream(input, format,
                            audio.length / format.getFrameSize());


            DataLine.Info info = new DataLine.Info(
                    SourceDataLine.class, format);
            final SourceDataLine line = (SourceDataLine)
                    AudioSystem.getLine(info);
            line.open(format);
            line.start();

            Runnable runner = new Runnable() {
                int bufferSize = (int) format.getSampleRate()
                        * format.getFrameSize();
                byte buffer[] = new byte[bufferSize];

                public void run() {
                    try {
                        int count;
                        while ((count = ais.read(
                                buffer, 0, buffer.length)) != -1) {
                            if (count > 0) {
                                line.write(buffer, 0, count);
                            }
                        }
                        line.drain();
                        line.close();
                    } catch (IOException e) {
                        System.err.println("I/O problems: " + e);
                        System.exit(-3);
                    }
                }
            };
            Thread playThread = new Thread(runner);
            playThread.start();

            /*//try to save file
            try {
                AudioSystem.write(ais, fileType, wavFile);
            } catch (IOException e) {
                e.printStackTrace();
            }      */

        } catch (LineUnavailableException e) {
            System.err.println("Line unavailable: " + e);
            System.exit(-4);
        }
    }

    private void saveAudio() {
        byte audio[] = out.toByteArray();
        InputStream input =
                new ByteArrayInputStream(audio);
        final AudioFormat format = getFormat();
        final AudioInputStream ais =
                new AudioInputStream(input, format,
                        audio.length / format.getFrameSize());

        //try to save file
        try {
            AudioSystem.write(ais, fileType, wavFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private AudioFormat getFormat() {
        // define encoding format
        float sampleRate = 48000;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate,
                sampleSizeInBits, channels, signed, bigEndian);
    }

    public static void main(String args[]) {
        JFrame frame = new Capture();
        frame.pack();
        frame.show();
    }


}
