package kz.iitu.java.userserviceclient.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;

public class BodyWriter {
    public static void bodyWriter(PrintWriter printWriter , Object object , Class type){
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            jsonMapper.writeValue(printWriter , type.cast(object));
        }catch (IOException e){
            System.err.println("BodyWriting exception. Exception message : " + e.getLocalizedMessage());
        }
        finally {
            printWriter.flush();
            printWriter.close();
        }
    }
}
