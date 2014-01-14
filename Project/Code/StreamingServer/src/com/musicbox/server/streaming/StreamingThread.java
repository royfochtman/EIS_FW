package com.musicbox.server.streaming;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author David Wachs
 */
public class StreamingThread implements Runnable {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String audioFileName;

    public StreamingThread(HttpServletRequest request, HttpServletResponse response, String audioFileName){
        this.request = request;
        this.response = response;
        this.audioFileName = audioFileName;
    }

    @Override
    public void run() {
        try {
            Path path = Paths.get(StreamingServlet.audioFilePath + audioFileName);
            if(path.toFile().exists()){
                byte[] bytes = Files.readAllBytes(path);
                OutputStream out = response.getOutputStream();
                out.write(bytes);
                out.flush();
                out.close();
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException ex) {
            ex.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
