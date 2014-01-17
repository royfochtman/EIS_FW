package com.musicbox.util.audio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author David Wachs
 */
public abstract class ByteHelper {

    public static long byteArrayToLong(byte[] b) {
        ByteBuffer bb = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN);
        bb.rewind();
        long value = bb.getInt();

        //Andere Möglichkeit
        /*long value =
                ((b[0] & 0xFF) <<  0) |
                ((b[1] & 0xFF) <<  8) |
                ((b[2] & 0xFF) << 16) |
                ((b[3] & 0xFF) << 24);*/

        return value;
    }

    public static int byteArrayToInt(byte[] b) {
        ByteBuffer bb = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN);
        bb.rewind();
        int val = bb.getShort();
        return val;
    }

    public static byte[] shortToByteArray(short data) {
        ByteBuffer buffer = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(data);
        return buffer.array();
        //return new byte[]{(byte) (data & 0xff), (byte) ((data >>> 8) & 0xff)};
    }

    public static byte[] intToByteArray(int data) {
        ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(data);
        return buffer.array();
    }
}
