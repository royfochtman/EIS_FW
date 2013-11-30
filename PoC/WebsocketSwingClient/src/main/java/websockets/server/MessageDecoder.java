package main.java.websockets.server;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 16.11.13
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class MessageDecoder implements Decoder.BinaryStream<RoomMessage> {
    @Override
    public RoomMessage decode(InputStream inputStream) throws DecodeException, IOException {
        try
        {
            ObjectInputStream in = new ObjectInputStream(inputStream);
            RoomMessage message = (RoomMessage)in.readObject();
            in.close();
            inputStream.close();
            return message;  //To change body of implemented methods use File | Settings | File Templates.
        }
        catch (ClassNotFoundException ex)
        {
            return null;
        }

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
