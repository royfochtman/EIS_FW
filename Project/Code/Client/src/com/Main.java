package com;

import com.musicbox.util.globalobject.GlobalObject;
import com.musicbox.util.websocket.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.websocket.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

@ClientEndpoint(encoders = {WebsocketMessageEncoder.class, WebsocketTextMessageEncoder.class},
        decoders = {WebsocketMessageDecoder.class, WebsocketTextMessageDecoder.class})
public class Main extends Application {
    private String streamingServerURLString; // entweder fest definieren oder beim starten des Clients auslesen
    private String websocketServerURIString; // muss hier fest definiert werden oder beim starten ausgelesen werden
    private String audioFilePath; // Pfad in dem die Audiodateien gespeichert werden => Cache
    private String roomName; // Der Name des MusikRooms in dem sich der Client befindet
    private Session session; //Wird mit der Methode connectToWebsocketServer() gesetzt

    public static Stage primaryStage;

    @OnOpen
    public void onOpen(Session session) {
        /**
         * Hier muss vielleicht eine Meldung für den Benutzer angezeigt werden, dass die Verbindung aufgebaut wurde
         */
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        /**
         * Meldung????
         */
    }

    @OnMessage
    public void onMessage(WebsocketMessage message, Session session) {
        switch ( message.getMessageType()){
            case NEW_MUSIC_ROOM:
                /**
                 * Music Room erfolgreich erstellt, dann weitere verwarbeitung hier
                 * Die Variable roomName muss hier auf den Namen des MusicRooms der erstellt wurde gesetzt werden.
                 */
                break;
            case JOIN_MUSIC_ROOM:
                /**
                 * Benutzer wurde vom Server zum Music_Room hinzugefügt => WebsocketMessage-Object enthält komplette
                 * Music-Room-Daten. weitere verarbeitung der Daten hier.
                 * Die Varaiable roomName muss hier auf den Namen des MusicRooms gesetzt werden.
                 */
                break;
            case CREATED_ELEMENT:
                /**
                 * Neues Element (Track, MusicSegment usw..) wurde vom Server erstellt. Weitere verarbeitung hier
                 */
                break;
            case UPDATED_ELEMENT:
                /**
                 * Element (Track, MusicSegment usw..) wurde vom Server geändert. Weitere verarbeitung hier
                 */
                break;
            case DELETED_ELEMENT:
                /**
                 * Element (Track, MusicSegment usw..) wurde vom Server gelöscht. Weitere verarbeitung hier
                 */
                break;
        }
    }

    @OnMessage
    public void onMessage(WebsocketTextMessage websocketChatMessage, Session session) {
        switch (websocketChatMessage.getTextMessageType()){
            case CHAT:
                /**
                 * Hier Inhalt der Chat-Nachricht im Chat-Fenster darstellen
                 */
                break;
            case ERROR:
                /**
                 * Hier Error-Nachricht verarbeiten
                 */
                break;
        }

    }

    /**
     * Sendet eine Audio-Datei an den Streaming-Server
     * @param audioFile
     */
    private void sendAudio(File audioFile) {
        try{
            URL url = new URL(streamingServerURLString + "?option=2&audioFileName=" + audioFile);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            //connection.setDoInput(true);
            FileBody fileBody = new FileBody(audioFile);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setMode(HttpMultipartMode.STRICT);
            multipartEntityBuilder.addPart("file", fileBody);
            HttpEntity entity = multipartEntityBuilder.build();
            connection.setRequestProperty("Content-Type", entity.getContentType().getValue());
            OutputStream outputStream = connection.getOutputStream();
            entity.writeTo(outputStream);
            outputStream.close();
            String response = connection.getResponseMessage();

            /**
             * Ab hier Response-Meldung verarbeiten
             */
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Bekommt vom Streaming Server eine angeforderte AudioDatei und speichert diese im Cache
     * @param fileName
     */
    private void getAudioFile(String fileName) {
        try {
            URL url = new URL(streamingServerURLString + "?option=1&audioFileName=" + fileName);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            //connection.setDoOutput(true);
            connection.setDoInput(true);

            InputStream in = connection.getInputStream();
            int byteValue;
            ArrayList<Byte> bytes = new ArrayList<>();
            while((byteValue = in.read()) != -1){
                byte b = (byte)byteValue;
                bytes.add(b);
            }
            String response = connection.getResponseMessage();
            byte[] bytesArray = new byte[bytes.size()];
            int i = 0;
            for(byte b : bytes){
                bytesArray[i++] = b;
            }
            FileOutputStream out = new FileOutputStream(audioFilePath);
            out.write(bytesArray);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sendet eine Chat-Message an den WebsocketServer
     * @param message
     */
    private void sendChatMessage(String message) {
        try{
            if(roomName != null)
            {
                WebsocketTextMessage chatMessage = new WebsocketTextMessage(roomName, WebsocketTextMessageType.CHAT, message);
                session.getBasicRemote().sendObject(chatMessage);
            }
        }catch(IOException | EncodeException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sendet ein beliebiges GlobalObject zum WebsocketServer
     * @param globalObject kann ein DatabaseEntity-Object sein
     * @param type MessageType (z.B. ELEMENT_CREATED)
     */
    public void sendObjectMessage(GlobalObject globalObject, WebsocketMessageType type) {
        ArrayList<GlobalObject> data = new ArrayList<>();
        data.add(globalObject);
        WebsocketMessage websocketMessage = new WebsocketMessage(roomName, type, data);
        try {
            session.getBasicRemote().sendObject(websocketMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Baut eine Verbindung zum WebsocketServer auf
     * Muss beim Starten des Clients aufgerufen werden
     */
    private void connectToWebsocketServer() {
        try
        {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(Main.class, new URI(websocketServerURIString));
        }
        catch(DeploymentException | IOException | URISyntaxException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * schließt die Verbindung zum WebsocketServer
     * Aufrufen beim schließen des Clients
     */
    public void closeConnection(){
        try {
            if(session != null && session.isOpen())
                session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Client disconnect"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/client_ui.fxml"));
        primaryStage.setTitle("Client UI");
        primaryStage.setScene(new Scene(root, 1300, 700));
        primaryStage.setMinWidth(1300);
        primaryStage.setMinHeight(600);
        primaryStage.centerOnScreen();
        primaryStage.show();

        this.primaryStage = primaryStage;

    }


    public static void main(String[] args) {
        launch(args);

    }
}
