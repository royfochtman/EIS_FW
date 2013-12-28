package com.musicbox.server.websocket;


import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by David on 17.12.13.
 * This Configurator is used to configure the Websocket-Server-Endpoint.
 * This has the effect, that every Client uses the same WebsocketServerEndpoint-Instance.
 * Without this configurator, for every Client would be created a single Instance of WebsocketServerEndpoint-Class.
 */
public class WebsocketServerEndpointConfigurator extends ServerEndpointConfig.Configurator {
    private static WebsocketServerEndpoint endpoint = new WebsocketServerEndpoint();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        if (WebsocketServerEndpoint.class.equals(endpointClass)) {
            return (T)endpoint;
        } else {
            throw new InstantiationException();
        }
    }
}
