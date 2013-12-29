package com.musicbox.server.streaming;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        byte[] bytes;
        try {
            switch (option) {
                case 1:
                    Path path = Paths.get("\\AudioFiles\\" + audioFileName);
                    if(path.toFile().exists()){
                        bytes = Files.readAllBytes(path);
                        OutputStream out = response.getOutputStream();
                        response.setStatus(HttpServletResponse.SC_OK);
                        out.write(bytes);
                        out.flush();
                        out.close();
                    }
                    break;
                case 2:
                    int byteValue;
                    ArrayList<Byte> byteArrayList = new ArrayList<>();
                    InputStream in = request.getInputStream();
                    while((byteValue = in.read()) != -1)
                        byteArrayList.add((byte)byteValue);

                    bytes = new byte[byteArrayList.size()];
                    int i = 0;
                    for(byte b : byteArrayList)
                        bytes[i++] = b;

                    File file = new File("\\AudioFiles\\" + audioFileName);
                    if(!file.exists() && file.createNewFile()) {
                        FileOutputStream outputStream = new FileOutputStream(file);
                        outputStream.write(bytes);
                        outputStream.close();
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
