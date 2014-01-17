package com.musicbox.junit;

import com.musicbox.util.audio.WaveFile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

/**
 * Created by David on 16.01.14.
 */
public class WaveFileTest {
    WaveFile waveFile;
    WaveFile waveFile2;
    File file;
    @Before
    public void setUp() throws Exception {
        file = new File("C:\\Users\\David\\Desktop\\Record.wav");
        waveFile2 = WaveFile.readFile(file);
    }

    @Test
    public void testReadFile() throws Exception {
        waveFile = WaveFile.readFile(file);
        assertNotNull(waveFile);
    }

    @Test
    public void  testWaveFile() throws Exception {
        WaveFile wave = new WaveFile(waveFile2.getAudioFormat(), waveFile2.getNumChannels(),waveFile2.getSampleRate(), waveFile2.getBitsPerSample(), waveFile2.getData(), waveFile2.getName());
        assertTrue(wave.equals(waveFile2));
    }

    @Test
    public void testFromWave() throws Exception {

    }

    @Test
    public void testSaveOnDisk() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {

    }

    @Test
    public void testHashCode() throws Exception {

    }
}
