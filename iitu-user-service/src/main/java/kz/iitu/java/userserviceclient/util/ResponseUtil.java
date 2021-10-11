package kz.iitu.java.userserviceclient.util;

import kz.iitu.java.userserviceclient.payload.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static ResponseEntity<?> response(Object o){
        if (o instanceof String)
            return new ResponseEntity<>(SuccessResponse.builder()
                    .status(200)
                    .message((String) o)
                    .build() , HttpStatus.OK);
        return new ResponseEntity<>(o , HttpStatus.OK);
    }

}
