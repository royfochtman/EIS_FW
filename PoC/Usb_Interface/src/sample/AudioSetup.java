package sample;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Roy
 * Date: 24.11.13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class AudioSetup {

    public static AudioFormat getFormat(){
        // define encoding format
        AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        /*return new AudioFormat(sampleRate,
            sampleSizeInBits, channels, signed, bigEndian);*/
        return new AudioFormat(encoding, sampleRate, sampleSizeInBits, channels,
                (sampleSizeInBits/8)*channels, sampleRate, bigEndian);

    }


}
