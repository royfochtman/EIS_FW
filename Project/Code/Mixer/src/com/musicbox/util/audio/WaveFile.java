package com.musicbox.util.audio;


import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.Arrays;

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

@author David Wachs
 */
public class WaveFile {
    /**
     * Should contains the letters "RIFF" in ASCII from
     */
    private String chunkID;
    /**
     * Chuncksize of wav-file. This is the size of the
     entire file in bytes minus 8 bytes for the
     two fields not included in this count:
     ChunkID and ChunkSize.
     chunkSize = 4 + (8 + SubChunk1Size) + (8 + SubChunk2Size)
     */
    private long chunkSize;
    /**
     * Should contains the letters "WAVE"
     */
    private String format;
    /**
     * Should contains the letters "fmt"
     */
    private String subChunk1ID;
    /**
     * Size of the rest of the SubChunk in bytes.
     */
    private long subChunk1Size;
    /**
     * PCM = 1, other values than 1 indicates a compression
     */
    private int audioFormat;
    /**
     * Mono = 1, Stereo = 2
     */
    private long numChannels;
    /**
     * For example 8000, 44100 etc.
     */
    private long sampleRate;
    /**
     * ByteRate = sampleRate * numChannels * bitsPerSample/8
     */
    private long byteRate;
    /**
     * BlockAlign = numChannels * bitsPerSample/8
     */
    private int blockAlign;
    /**
     * for example: 8 bits = 8, 16 bits = 16 etc.
     */
    private int bitsPerSample;
    /**
     * Should contains the letters "data
     */
    private String subChunk2ID;
    /**
     * Number of bytes in the data.
     * = sampleRate * numChannels * bitsPerSample/8
     */
    private long subChunk2Size;
    /**
     * The sound data
     */
    private byte[] data;
    /**
     * Name of the file
     */
    private String name;
    /**
     * Length of audio data in seconds
     */
    private double audioLength;

    /**
     * Constructor,only needs a few information about the wave-file. All other information, e.g. chunkSize or
     * byteRate are calculated with the given information
     * @param audioFormat audioFormat of the file, PCM = 1, other values indicates a compression
     * @param numChannels number of channels (Mono = 1, Stereo = 2)
     * @param sampleRate sample rate of the audio data
     * @param bitsPerSample bits per sample
     * @param data the actual audio data
     * @param name name of the file
     */
    public WaveFile(int audioFormat, long numChannels, long sampleRate, int bitsPerSample, byte[] data, String name) {
        this.chunkID = "RIFF";
        this.format = "WAVE";
        this.subChunk1ID = "fmt ";
        this.subChunk2ID = "data";
        this.subChunk1Size = 16;
        this.bitsPerSample = bitsPerSample;
        this.sampleRate = sampleRate;
        this.audioFormat = audioFormat;
        this.numChannels = numChannels;
        if(data == null)
            this.subChunk2Size = 0L;
        this.data = data;
        this.name = name;

        calculateChunkSize();
        calculateByteRate();
        calculateBlockAlign();
        calculateAudioLength();
    }

    public WaveFile() {
        this.chunkID = "RIFF";
        this.format = "WAVE";
        this.subChunk1ID = "fmt ";
        this.subChunk2ID = "data";
        this.chunkSize = 0L;
        this.subChunk1Size = 0L;
        this.audioFormat = 0;
        this.numChannels = 0L;
        this.sampleRate = 0L;
        this.byteRate = 0L;
        this.blockAlign = 0;
        this.bitsPerSample = 0;
        this.subChunk2Size = 0L;
        this.data = null;
        this.name = "";
        this.audioLength = 0D;
    }

    public long getChunkSize() {
        return chunkSize;
    }

    /**
     * Calculates the value of chunkSize
     *  = 4 + (8 + subChunk1Size) + (8 + subChunk2Size)
     */
    private void calculateChunkSize() {
        this.chunkSize = 4 + (8 + this.subChunk1Size) + (8 + this.subChunk2Size) +40;
    }

    public long getSubChunk1Size() {
        return subChunk1Size;
    }

    public int getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(int audioFormat) {
        this.audioFormat = audioFormat;
    }

    public long getNumChannels() {
        return numChannels;
    }

    /**
     * Sets the number of channels to 1 (Mono) or 2 (Stereo)<br></br>
     * after that the byte rate and block align alue are recalculated
     * @param numChannels number of channels (Mono = 1, Stereo = 2)
     */
    public void setNumChannels(long numChannels) {
        if(numChannels == 1 || numChannels == 2){
            this.numChannels = numChannels;
            calculateByteRate();
            calculateBlockAlign();
        }
    }

    public long getSampleRate() {
        return sampleRate;
    }

    /**
     * Sets the sampleRate value. After that
     * the byte rate value is recalculated
     * @param sampleRate Sample rate, e.g. 8000 or 44100 etc.
     */
    public void setSampleRate(long sampleRate) {
        this.sampleRate = sampleRate;
        calculateByteRate();
    }

    public long getByteRate() {
        return byteRate;
    }

    /**
     * Calculates the byte rate value following calculation:
     * sampleRate * numChannels * (bitsPerSample/8)
     */
    private void calculateByteRate() {
        this.byteRate = this.sampleRate * this.numChannels * (this.bitsPerSample/8);
    }

    public int getBlockAlign() {
        return blockAlign;
    }

    /**
     * Calculates the block align value with following calculation:
     * numChannels * (bitsPerSample/8)
     */
    private void calculateBlockAlign() {
        this.blockAlign = (int)this.numChannels * (this.bitsPerSample/8);
    }

    public int getBitsPerSample() {
        return bitsPerSample;
    }

    /**
     * Sets the value of bitsPerSample, e.g. 8, 16, etc.<br></br>
     * After that the byte rate and the block align values are recalculated
     * @param bitsPerSample bits per sample ( 8 bits = 8, 16 bits = 16, ect.)
     */
    public void setBitsPerSample(int bitsPerSample) {
        this.bitsPerSample = bitsPerSample;
        calculateByteRate();
        calculateBlockAlign();
    }

    public long getSubChunk2Size() {
        return subChunk2Size;
    }

    /**
     * Sets the value of subChunk2Size <br></br>
     * After that the chunkSize and the audioLength values are recalculated
     * @param subChunk2Size subChunk2Size value in bytes
     */
    private void setSubChunk2Size(long subChunk2Size) {
        this.subChunk2Size = subChunk2Size;
        calculateChunkSize();
        calculateAudioLength();
    }

    public byte[] getData() {
        return data;
    }

    /**
     * Sets the audio data. <br></br>
     * After that the subChunk2Size value is recalculated
     * @param data the actual audio data
     */
    public void setData(byte[] data) {
        this.data = data;
        setSubChunk2Size(data.length);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public String getSubChunk1ID() {
        return subChunk1ID;
    }

    public double getAudioLength() {
        return audioLength;
    }

    /**
     * Calculates the audio length value in seconds with following calculation:
     * subChunk2Size / sampleRate / numChannels / (bitsPerSample / 8)
     */
    private void calculateAudioLength() {
        if(this.subChunk2Size > 0L)
            this.audioLength = this.subChunk2Size / this.sampleRate / this.numChannels / (this.bitsPerSample / 8);
    }

    /**
     * Creates a new WaveFile-Object with the attributes of an existing object.
     * Only the following attributes are taken from waveFile-Param to create new WaveFile-Object: <br></br>
     * audioFormat, numChannels, sampleRate, bitsPerSample
     * @param waveFile WaveFile-Object, from which the attributes are taken over
     * @return Returns new WaveFile-Object
     */
    public static WaveFile fromWave(WaveFile waveFile){
        return new WaveFile(waveFile.audioFormat, waveFile.numChannels, waveFile.sampleRate, waveFile.bitsPerSample, null, "");
    }

    /**
     * Read a file and checks, if it is a wav-file and store the data in a WaveFile-Object
     * @param file  File to read
     * @return  WaveFile-Object with the data of the file
     */
    public static WaveFile readFile(File file) {
        WaveFile waveFile = new WaveFile();

        if(file == null || !file.exists() || !FilenameUtils.getExtension(file.getAbsolutePath()).equals("wav"))
            return null;
        waveFile.setName(file.getName());

        DataInputStream in;
        //waveFile.audioData = null;
        byte[] tmpInt = new byte[4];
        byte[] tmpShort = new byte[2];
        try
        {
            in = new DataInputStream(new FileInputStream(file));

            waveFile.chunkID = "" + (char) in.readByte() + (char) in.readByte() + (char) in.readByte() + (char) in.readByte();

            in.read(tmpInt); // read the ChunkSize
            waveFile.chunkSize = ByteHelper.byteArrayToLong(tmpInt);

            waveFile.format = "" + (char) in.readByte() + (char) in.readByte() + (char) in.readByte() + (char) in.readByte();

            waveFile.subChunk1ID = "" + (char) in.readByte() + (char) in.readByte() + (char) in.readByte() + (char) in.readByte();

            in.read(tmpInt); // read the SubChunk1Size
            waveFile.subChunk1Size = ByteHelper.byteArrayToLong(tmpInt);

            in.read(tmpShort); // read the audio format.  This should be 1 for PCM
            waveFile.audioFormat = ByteHelper.byteArrayToInt(tmpShort);

            in.read(tmpShort); // read the # of channels (1 or 2)
            waveFile.numChannels = ByteHelper.byteArrayToInt(tmpShort);

            in.read(tmpInt); // read the samplerate
            waveFile.sampleRate = ByteHelper.byteArrayToLong(tmpInt);

            in.read(tmpInt); // read the byterate
            waveFile.byteRate = ByteHelper.byteArrayToLong(tmpInt);

            in.read(tmpShort); // read the blockalign
            waveFile.blockAlign = ByteHelper.byteArrayToInt(tmpShort);

            in.read(tmpShort); // read the bitspersample
            waveFile.bitsPerSample = ByteHelper.byteArrayToInt(tmpShort);

            // read the data chunk header - reading this IS necessary, because not all wav files will have the data chunk here - for now, we're just assuming that the data chunk is here
            waveFile.subChunk2ID = "" + (char) in.readByte() + (char) in.readByte() + (char) in.readByte() + (char) in.readByte();

            in.read(tmpInt); // read the size of the data
            waveFile.subChunk2Size = ByteHelper.byteArrayToLong(tmpInt);


            // read the data chunk
            waveFile.data = new byte[(int) waveFile.subChunk2Size];
            in.read(waveFile.data);

            // close the input stream
            in.close();

            waveFile.calculateAudioLength();

            return waveFile;
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    /**
     * Saves the given WaveFile-Object as wav-file at a specific path with the date stored in the WaveFile-Object
     * @param path path were the file has to be created
     * @return true if success, otherwise returns false
     */
    public boolean saveOnDisk(String path){
        try {
            File file = new File(path + "\\" + this.name);
            if(!file.exists())
                file.createNewFile();
            else
                return false;

            DataOutputStream fileOut = new DataOutputStream(new FileOutputStream(file));
            fileOut.writeBytes(this.chunkID);                 // 00 - RIFF
            fileOut.write(ByteHelper.intToByteArray((int) this.chunkSize), 0, 4);     // 04 - how big is the rest of this file?
            fileOut.writeBytes(this.format);                 // 08 - WAVE
            fileOut.writeBytes(this.subChunk1ID);                 // 12 - fmt
            fileOut.write(ByteHelper.intToByteArray((int) this.subChunk1Size), 0, 4); // 16 - size of this chunk
            fileOut.write(ByteHelper.shortToByteArray((short) this.audioFormat), 0, 2);        // 20 - what is the audio format? 1 for PCM = Pulse Code Modulation
            fileOut.write(ByteHelper.shortToByteArray((short) this.numChannels), 0, 2);  // 22 - mono or stereo? 1 or 2?  (or 5 or ???)
            fileOut.write(ByteHelper.intToByteArray((int) this.sampleRate), 0, 4);        // 24 - samples per second (numbers per second)
            fileOut.write(ByteHelper.intToByteArray((int) this.byteRate), 0, 4);      // 28 - bytes per second
            fileOut.write(ByteHelper.shortToByteArray((short) this.blockAlign), 0, 2);    // 32 - # of bytes in one sample, for all channels
            fileOut.write(ByteHelper.shortToByteArray((short) this.bitsPerSample), 0, 2); // 34 - how many bits in a sample(number)?  usually 16 or 24
            fileOut.writeBytes(this.subChunk2ID);                 // 36 - data
            fileOut.write(ByteHelper.intToByteArray((int)this.subChunk2Size), 0, 4);
            fileOut.write(this.data);
            fileOut.close();

            return true;
        } catch(IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WaveFile waveFile = (WaveFile) o;

        return audioFormat == waveFile.audioFormat
                && bitsPerSample == waveFile.bitsPerSample
                && blockAlign == waveFile.blockAlign
                && byteRate == waveFile.byteRate
                && numChannels == waveFile.numChannels
                && chunkSize == waveFile.chunkSize
                && subChunk2Size == waveFile.subChunk2Size
                && sampleRate == waveFile.sampleRate
                && subChunk1Size == waveFile.subChunk1Size
                && data.equals(waveFile.data)
                && name != null && name.equals(waveFile.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (chunkSize ^ (chunkSize >>> 32));
        result = 31 * result + (int) (subChunk1Size ^ (subChunk1Size >>> 32));
        result = 31 * result + audioFormat;
        result = 31 * result + (int) (numChannels ^ (numChannels >>> 32));
        result = 31 * result + (int) (sampleRate ^ (sampleRate >>> 32));
        result = 31 * result + (int) (byteRate ^ (byteRate >>> 32));
        result = 31 * result + blockAlign;
        result = 31 * result + bitsPerSample;
        result = 31 * result + (int) (subChunk2Size ^ (subChunk2Size >>> 32));
        result = 31 * result + (data != null ? Arrays.hashCode(data) : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }



}
