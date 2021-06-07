package kz.spring.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomNotFoundException extends RuntimeException{
    public CustomNotFoundException(){
        super();
    }

    public CustomNotFoundException(String s){
        super(s);
    }

    public CustomNotFoundException(String s , Throwable throwable){
        super(s , throwable);
    }
}

