package com.musicbox.util.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by David on 17.12.13.
 */
public class WebsocketMessageEncoder implements Encoder.BinaryStream<WebsocketMessage> {
        @Override
        public void encode(WebsocketMessage websocketMessage, OutputStream outputStream) throws EncodeException, IOException {
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(websocketMessage);
            out.close();
            outputStream.close();
        }

        @Override
        public void init(EndpointConfig endpointConfig) {

        }

        @Override
        public void destroy() {

        }
}
