package com.musicbox.util.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Created by David on 17.12.13.
 */
public class WebsocketMessageDecoder implements Decoder.BinaryStream<WebsocketMessage> {
    @Override
    public WebsocketMessage decode(InputStream inputStream) throws DecodeException, IOException {
        try {
            ObjectInputStream in = new ObjectInputStream(inputStream);
            WebsocketMessage websocketMessage = (WebsocketMessage)in.readObject();
            in.close();
            inputStream.close();
            return websocketMessage;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
