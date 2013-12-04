import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 29.11.13
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public class Mixer{
    private static File f1 = new File("AudioFiles\\Source\\Record.wav");
    private static File f2 = new File("AudioFiles\\Source\\Record2.wav");

    private static File outputFile = new File("AudioFiles\\Out\\out.wav");

    /**
     * Concatenate two audio WAV files
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public static void concatenate() throws IOException, UnsupportedAudioFileException {
        AudioInputStream stream1 = AudioSystem.getAudioInputStream(f1);
        AudioInputStream stream2 = AudioSystem.getAudioInputStream(f2);
        SequenceInputStream in = new SequenceInputStream(stream1, stream2);
        AudioInputStream append = new AudioInputStream(in, stream1.getFormat(), stream1.getFrameLength() + stream2.getFrameLength());
        AudioSystem.write(append, AudioFileFormat.Type.WAVE, outputFile);
    }

    public static Boolean manuelConcatenate() throws UnsupportedAudioFileException, IOException {
        WaveFile file1 = new WaveFile(f1);
        WaveFile file2 = new WaveFile(f2);
        file1.concatenateWave(file2.getData(), file2.getDataSize(), outputFile);
        return true;
    }

    /**
     * Mix two WAV-Files with the MixingAudioInputStream-Class. Tritonus API is required!
     * @return Mixed audio data as ByteArrayOutpuStream
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public static ByteArrayOutputStream mix1() throws IOException, UnsupportedAudioFileException {
        AudioInputStream stream1 = AudioSystem.getAudioInputStream(f1);
        AudioInputStream stream2 = AudioSystem.getAudioInputStream(f2);

        Collection list = new ArrayList();
        list.add(stream1);
        list.add(stream2);
        AudioFormat format = AudioSetup.getFormat();

        int bufferSize = (int)format.getSampleRate()* format.getFrameSize();
        //int bufferSize = (int)stream2.getFrameLength() + (int)stream2.getFrameLength();
        byte buffer[] = new byte[bufferSize];
        MixingAudioInputStream stream = new MixingAudioInputStream(format, list);
        int nBytesRead = stream.read(buffer, 0, buffer.length);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(buffer, 0, nBytesRead);
        return out;
    }

    public static Boolean manuelMixing() throws UnsupportedAudioFileException, IOException {
        WaveFile file1 = new WaveFile(f1);
        WaveFile file2 = new WaveFile(f2);
        file1.mixWave(file2.getData(), file2.getDataSize(), outputFile, 5);
        return true;
    }
}
