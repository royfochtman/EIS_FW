import javax.sound.sampled.AudioFormat;

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
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate,
            sampleSizeInBits, channels, signed, bigEndian);

    }


}
