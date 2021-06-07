package kz.spring.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CustomBadRequestException extends RuntimeException{
    public CustomBadRequestException(){
        super();
    }

    public CustomBadRequestException(String s){
        super(s);
    }

    public CustomBadRequestException(String s , Throwable throwable){
        super(s , throwable);
    }
}

