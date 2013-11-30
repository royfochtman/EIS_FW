package main.java.websockets.server;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 16.11.13
 * Time: 15:55
 * To change this template use File | Settings | File Templates.
 */
public class MessageEncoder implements Encoder.BinaryStream<RoomMessage> {

    @Override
    public void encode(RoomMessage roomMessage, OutputStream outputStream) throws EncodeException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(roomMessage);
        out.close();
        outputStream.close();
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
