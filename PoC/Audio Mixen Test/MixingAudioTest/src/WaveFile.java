/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 29.11.13
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Quelle: http://stackoverflow.com/questions/5810164/how-can-i-write-wav-file-from-byte-array-in-java
 * WAVE-File-Format: https://ccrma.stanford.edu/courses/422/projects/WaveFormat/
 *
 *
 The thing that makes reading wav files tricky is that java has no unsigned types.  This means that the
 binary data can't just be read and cast appropriately.  Also, we have to use larger types
 than are normally necessary.

 In many languages including java, an integer is represented by 4 bytes.  The issue here is
 that in most languages, integers can be signed or unsigned, and in wav files the  integers
 are unsigned.  So, to make sure that we can store the proper values, we have to use longs
 to hold integers, and integers to hold shorts.

 Then, we have to convert back when we want to save our wav data.

 ChunkID            =>  Big Endian
 ChunkSize          =>  Little Endian
 Format             =>  Big Endian
 Subchunk1ID        =>  Big Endian
 Subchunk1Size      =>  Little Endian
 AudioFormat        =>  Little Endian
 NumChannels        =>  Little Endian
 SampleRate         =>  Little Endian
 ByteRate           =>  Little Endian
 BlockAlign         =>  Little Endian
 BitsPerSample      =>  Little Endian
 Subchunk2ID        =>  Big Endian
 Subchunk2Size      =>  Little Endian
 Data               =>  Little Endian
 */
public class WaveFile {
    private long chunkSize;
    private long subChunk1Size;
    private int audioFormat;
    private long channels;
    private long sampleRate;
    private long byteRate;
    private int blockAlign;
    private int bitsPerSample;
    private long dataSize;
    public byte[] data;
    private File waveFile;

    /**
     * Constructor
     * @param wav = WAVE-File
     * @throws Exception if file could not be read
     */
    public WaveFile(File wav) throws UnsupportedAudioFileException {
        if(wav.exists()){
            waveFile = wav;
            if(!read())
                throw new UnsupportedAudioFileException("Could not read file!");
        }
    }

    public long getDataSize(){ return dataSize; }
    public byte[] getData() { return data; }

    /**
     * Read the WAV-File and save the parts
     * @return true, if success
     */
   private boolean read()
   {
       DataInputStream in = null;
       data = null;
       byte[] tmpInt = new byte[4];
       byte[] tmpShort = new byte[2];
       try
       {
           in = new DataInputStream(new FileInputStream(waveFile));

           String chunkID = "" + (char) in.readByte() + (char) in.readByte() + (char) in.readByte() + (char) in.readByte();

           in.read(tmpInt); // read the ChunkSize
           chunkSize = ByteHelper.byteArrayToLong(tmpInt);

           String formatString = "" + (char) in.readByte() + (char) in.readByte() + (char) in.readByte() + (char) in.readByte();

           String subChunk1ID = "" + (char) in.readByte() + (char) in.readByte() + (char) in.readByte() + (char) in.readByte();

           in.read(tmpInt); // read the SubChunk1Size
           subChunk1Size = ByteHelper.byteArrayToLong(tmpInt);

           in.read(tmpShort); // read the audio format.  This should be 1 for PCM
           audioFormat = ByteHelper.byteArrayToInt(tmpShort);

           in.read(tmpShort); // read the # of channels (1 or 2)
           channels = ByteHelper.byteArrayToInt(tmpShort);

           in.read(tmpInt); // read the samplerate
           sampleRate = ByteHelper.byteArrayToLong(tmpInt);

           in.read(tmpInt); // read the byterate
           byteRate = ByteHelper.byteArrayToLong(tmpInt);

           in.read(tmpShort); // read the blockalign
           blockAlign = ByteHelper.byteArrayToInt(tmpShort);

           in.read(tmpShort); // read the bitspersample
           bitsPerSample = ByteHelper.byteArrayToInt(tmpShort);

           // read the data chunk header - reading this IS necessary, because not all wav files will have the data chunk here - for now, we're just assuming that the data chunk is here
           String dataChunkID = "" + (char) in.readByte() + (char) in.readByte() + (char) in.readByte() + (char) in.readByte();

           in.read(tmpInt); // read the size of the data
           dataSize = ByteHelper.byteArrayToLong(tmpInt);


           // read the data chunk
           data = new byte[(int) dataSize];
           in.read(data);

           // close the input stream
           in.close();

           return true;
       }
       catch(Exception ex)
       {
          return false;
       }
   }

   private DataOutputStream writeHeader(DataOutputStream out) throws IOException {
       //DataOutputStream out = new DataOutputStream(new ByteArrayOutputStream());
       // write the wav file per the wav file format
       out.writeBytes("RIFF");                 // 00 - RIFF
       out.write(ByteHelper.intToByteArray((int) chunkSize), 0, 4);     // 04 - how big is the rest of this file?
       out.writeBytes("WAVE");                 // 08 - WAVE
       out.writeBytes("fmt ");                 // 12 - fmt
       out.write(ByteHelper.intToByteArray((int) subChunk1Size), 0, 4); // 16 - size of this chunk
       out.write(ByteHelper.shortToByteArray((short) audioFormat), 0, 2);        // 20 - what is the audio format? 1 for PCM = Pulse Code Modulation
       out.write(ByteHelper.shortToByteArray((short) channels), 0, 2);  // 22 - mono or stereo? 1 or 2?  (or 5 or ???)
       out.write(ByteHelper.intToByteArray((int) sampleRate), 0, 4);        // 24 - samples per second (numbers per second)
       out.write(ByteHelper.intToByteArray((int) byteRate), 0, 4);      // 28 - bytes per second
       out.write(ByteHelper.shortToByteArray((short) blockAlign), 0, 2);    // 32 - # of bytes in one sample, for all channels
       out.write(ByteHelper.shortToByteArray((short) bitsPerSample), 0, 2); // 34 - how many bits in a sample(number)?  usually 16 or 24
       out.writeBytes("data");                 // 36 - data
       return out;
   }

    /**
     * Concatenate two wave files
     * @param dataToAdd
     * @param dataToAddSize
     * @param destFile
     * @throws IOException
     */
   public void concatenateWave(byte[] dataToAdd, long dataToAddSize, File destFile) throws IOException {
       DataOutputStream out = writeHeader(writeFile(destFile));
       if(out != null)
       {
           out.write(ByteHelper.intToByteArray((int) dataSize + (int) dataToAddSize), 0, 4);
           ByteBuffer buff = ByteBuffer.allocate((int)dataSize + (int)dataToAddSize).order(ByteOrder.LITTLE_ENDIAN);
           buff.put(data);
           buff.put(dataToAdd);
           out.write(buff.array());
           out.close();
       }
   }

   private DataOutputStream writeFile(File destFile) throws FileNotFoundException {
       DataOutputStream fileOut = new DataOutputStream(new FileOutputStream(destFile));
       return fileOut;
   }

    /**
     * Mix two wave files together. Quelle: http://stackoverflow.com/questions/16810228/how-to-remove-noise-from-wav-file-after-mixed
     * @param dataToMix
     * @param dataToMixSize
     * @param destFile
     * @throws IOException
     */
   public void mixWave(byte[] dataToMix, long dataToMixSize, File destFile) throws IOException {
       DataOutputStream out = writeHeader(writeFile(destFile));
       byte[] mixedData;
       if(dataSize >= dataToMixSize){
           mixedData = new byte[(int)dataSize];
           out.write(ByteHelper.intToByteArray((int) dataSize), 0, 4);
       }
       else{
           mixedData = new byte[(int)dataToMixSize];
           out.write(ByteHelper.intToByteArray((int) dataToMixSize), 0, 4);
       }

       for(int i=0; i<mixedData.length; i++)
       {
           float samplef1 = 0;
           float samplef2 = 0;
           if(i<dataSize)
               samplef1 = data[i] / 128.0f;
           if(i<dataToMixSize)
               samplef2 = dataToMix[i] / 128.0f;

           float mixed = samplef1 + samplef2;
           // hard clipping
           if (mixed > 1.0f)  mixed = 1.0f;
           if (mixed < -1.0f) mixed = -1.0f;

           byte mixedByte = (byte) (mixed *128.0f);
           mixedData[i] = mixedByte;
       }
       out.write(mixedData);
       out.close();
   }
}
