package solutions.egen.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Item Not Found")
public class ItemNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public ItemNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}