package org.etutoria.courseapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CourseAlreadyExistsException extends RuntimeException {
    public CourseAlreadyExistsException(String message){
        super(message);
    }

}
