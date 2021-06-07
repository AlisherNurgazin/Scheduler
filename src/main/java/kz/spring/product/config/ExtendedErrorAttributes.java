package kz.spring.product.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class ExtendedErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String , Object> errorAttributes = super.getErrorAttributes(webRequest , options.excluding(ErrorAttributeOptions.Include.STACK_TRACE));
        errorAttributes.put("timestamp" , LocalDateTime.now().toString());
        return errorAttributes;
    }
}
