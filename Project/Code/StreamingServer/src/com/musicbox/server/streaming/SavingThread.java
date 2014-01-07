package com.musicbox.server.streaming;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
/*import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;*/
import java.io.IOException;
import java.util.List;

/**
 * Uses Apache CommonIO and Common-FileUpload API
 * Client has to use httpComponentsAPI
 *
 * @author David Wachs
 */
public class SavingThread implements Runnable {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String audioFileName;
    private ServletFileUpload uploader;

    public SavingThread(HttpServletRequest request, HttpServletResponse response, String audioFileName) {
        this.request = request;
        this.response = response;
        this.audioFileName = audioFileName;
    }

    @Override
    public void run() {
        try {
            if(!ServletFileUpload.isMultipartContent(request))
                response.sendError(HttpServletResponse.SC_NO_CONTENT);
            else {
                DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
                fileItemFactory.setRepository(new File("\\AudioFiles"));
                this.uploader = new ServletFileUpload(fileItemFactory);
                List<FileItem> fileItemList = uploader.parseRequest(request);
                if(fileItemList.size() == 1){
                    FileItem fileItem = fileItemList.get(0);
                    File file = new File("\\AudioFiles\\" + audioFileName);
                    fileItem.write(file);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                }
                else
                    response.sendError(HttpServletResponse.SC_NO_CONTENT);
            }


            //This approach don't work
            /*int byteValue;
            ArrayList<Byte> byteArrayList = new ArrayList<>();
            InputStream in = request.getInputStream();
            //request.get

            while((byteValue = in.read()) != -1)
                byteArrayList.add((byte)byteValue);

            in.close();

            byte[] bytes = new byte[byteArrayList.size()];
            int i = 0;
            for(byte b : byteArrayList)
                bytes[i++] = b;

            File file = new File("\\AudioFiles\\" + audioFileName);
            if(!file.exists()) {
                if(file.createNewFile()) {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(bytes);
                    outputStream.close();
                    response.setStatus(HttpServletResponse.SC_CREATED);
                }
                else
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
            else
                response.setStatus(HttpServletResponse.SC_CONFLICT);*/
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
