package com.musicbox.server.streaming;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

/**
 * @author David Wachs
 */
public class StreamingServlet extends HttpServlet {
    private final String OPTION_PARAM = "option";
    private final String AUDIO_FILE_NAME_PARAM = "audioFileName";

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        int option = Integer.parseInt(request.getParameter(OPTION_PARAM));
        String audioFileName = request.getParameter(AUDIO_FILE_NAME_PARAM);

        switch (option) {
            case 1:
                new StreamingThread(request, response, audioFileName).run();
                break;
            case 2:
                new SavingThread(request, response, audioFileName).run();
                break;
        }
    }

}
