package com.musicbox.server.streaming;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author David Wachs
 */
public class StreamingServlet extends HttpServlet {
    private final String AUDIO_FILE_NAME_PARAM = "audioFileName";
    protected static String audioFilePath;

    public void init(ServletConfig servletconfig) throws ServletException {
        audioFilePath = servletconfig.getInitParameter("audioFilePath");
        if(audioFilePath != null && !audioFilePath.isEmpty()){
            File directory = new File(audioFilePath);
            if(!directory.exists() && directory.isDirectory())
                directory.mkdir();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String audioFileName = request.getParameter(AUDIO_FILE_NAME_PARAM);
        try{
        if(audioFileName != null && !audioFileName.isEmpty())
            new SavingThread(request, response, audioFileName).run();
        else
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
        String audioFileName = request.getParameter(AUDIO_FILE_NAME_PARAM);
        if(audioFileName != null && !audioFileName.isEmpty())
            new StreamingThread(request, response, audioFileName).run();
        else
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
