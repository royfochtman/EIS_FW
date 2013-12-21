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
public class WebsocketMessageEncoder implements Encoder.BinaryStream<WebsocketChatMessage> {
        @Override
        public void encode(WebsocketChatMessage websocketChatMessage, OutputStream outputStream) throws EncodeException, IOException {
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(websocketChatMessage);
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
