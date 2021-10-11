package kz.iitu.java.userserviceclient.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomConflictException extends RuntimeException{
    public CustomConflictException(){
        super();
    }

    public CustomConflictException(String s){
        super(s);
    }

    public CustomConflictException(String s  , Throwable throwable){
        super(s , throwable);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

