package cz.cvut.fit.tjv.arails.app.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoEntityFoundException extends ResponseStatusException {
    public NoEntityFoundException(){
        super(HttpStatus.NOT_FOUND);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public NoEntityFoundException(String message) {
        super(HttpStatus.NOT_FOUND);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }
}
