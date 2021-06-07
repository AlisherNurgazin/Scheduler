package kz.spring.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CustomValidationException extends RuntimeException{
    public CustomValidationException(){
        super();
    }

    public CustomValidationException(String s){
        super(s);
    }

    public CustomValidationException(String s , Throwable throwable){
        super(s , throwable);
    }
}
