package com.musicbox.junit;

import com.musicbox.util.audio.WaveFile;
import com.musicbox.util.audio.WaveMixer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

/**
 * Created by David on 16.01.14.
 */
public class WaveMixerTest {
    WaveFile waveFile1;
    WaveFile waveFile2;
    @Before
    public void setUp() throws Exception {
        waveFile1 = WaveFile.readFile(new File("C:\\Users\\David\\Desktop\\Record.wav"));
        waveFile2 = WaveFile.readFile(new File("C:\\Users\\David\\Desktop\\Record2.wav"));
    }

    @Test
    public void testConcatenate() throws Exception {
        WaveFile w = WaveMixer.concatenate(waveFile1, waveFile2, 0);
        assertNotNull(w);
        w.setName("test.wav");
        assertTrue(w.saveOnDisk("C:\\Users\\David\\Desktop\\"));

        w = WaveMixer.concatenate(waveFile1, waveFile2, 5);
        assertNotNull(w);
        w.setName("test2.wav");
        assertTrue(w.saveOnDisk("C:\\Users\\David\\Desktop\\"));
    }

    @Test
    public void testMix() throws Exception {
        WaveFile w = WaveMixer.mix2(waveFile1, waveFile2, 0);
        assertNotNull(w);
        w.setName("test3.wav");
        assertTrue(w.saveOnDisk("C:\\Users\\David\\Desktop\\"));

        w = WaveMixer.mix(waveFile1, waveFile2, 5);
        assertNotNull(w);
        w.setName("test4.wav");
        assertTrue(w.saveOnDisk("C:\\Users\\David\\Desktop\\"));
    }
}
