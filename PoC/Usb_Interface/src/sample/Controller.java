package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;

import javax.sound.sampled.*;
import java.io.*;

public class Controller {
    @FXML private Button btn_record;
    @FXML private Button btn_play;
    @FXML private Button btn_stop;
    @FXML private Button btn_save;
    @FXML private ComboBox comboBox;
    @FXML private Button btn_loadInputs;

    private Mixer.Info[] aInfos;
    private int index;
    private Mixer mixer;
    ByteArrayOutputStream out;
    protected boolean running;

    // path of the wav file
    File wavFile = new File("E:/Dropbox/FH Köln/Medieninformatik/5. Sem/EIS/Record.wav");

    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    public void loadInputs(ActionEvent actionEvent) {

        aInfos = AudioSystem.getMixerInfo();
        //Mixer mixer = AudioSystem.getMixer(aInfos[0]);

        for (int i=0; i< aInfos.length; i++) {
            comboBox.getItems().add(i, aInfos[i].getName());

            System.out.println(aInfos[i].getName() + ". Description: " + aInfos[i].getDescription());
        }
            btn_loadInputs.setDisable(true);
    }

    public void handleComboBox(ActionEvent actionEvent) {
        index = comboBox.getSelectionModel().getSelectedIndex();
        System.out.println(comboBox.getValue().toString());
        System.out.println(comboBox.getSelectionModel().getSelectedIndex());

        mixer = AudioSystem.getMixer(aInfos[index]);
        btn_record.setDisable(false);
    }

    public void handleRecordBtn(ActionEvent actionEvent){

            try {
                //Format Setup
                final AudioFormat format = AudioSetup.getFormat();

                DataLine.Info info = new DataLine.Info(
                        TargetDataLine.class, format);
                /*final TargetDataLine line = (TargetDataLine)
                        AudioSystem.getLine(info);            */
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
        btn_stop.setDisable(false);


    }

    public void handleStopBtn(ActionEvent actionEvent) {
        btn_record.setDisable(false);
        btn_stop.setDisable(true);
        btn_play.setDisable(false);
        btn_save.setDisable(false);
        running = false;


    }

    public void handlePlayBtn() {
        try {
        byte audio[] = out.toByteArray();
        InputStream input =
                new ByteArrayInputStream(audio);
        final AudioFormat format = AudioSetup.getFormat();
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

    public void handleSaveBtn(ActionEvent actionEvent) {

        byte audio[] = out.toByteArray();
        InputStream input =
                new ByteArrayInputStream(audio);
        final AudioFormat format = AudioSetup.getFormat();
        final AudioInputStream ais =
                new AudioInputStream(input, format,
                        audio.length / format.getFrameSize());

        //try to save file
        try {AudioSystem.write(ais, fileType, wavFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
