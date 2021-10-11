package kz.iitu.java.userserviceclient.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CustomForbiddenException extends RuntimeException{

    public CustomForbiddenException(){
        super();
    }

    public CustomForbiddenException(String s){
        super(s);
    }

    public CustomForbiddenException(String s  , Throwable throwable){
        super(s , throwable);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
