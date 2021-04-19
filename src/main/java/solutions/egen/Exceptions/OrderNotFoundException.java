package solutions.egen.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Order Not Found")
public class OrderNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}