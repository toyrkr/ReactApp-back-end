package com.toy.ReactApp.user;

import com.toy.ReactApp.error.ApiError;
import com.toy.ReactApp.shared.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger( UserController.class );

    @Autowired
    UserService userService;

    @PostMapping("/api/v1/users")
    public GenericResponse createUser(@Valid @RequestBody User user){
        System.out.println(user);
        userService.save( user );
        return new GenericResponse("User created. Success..");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException manve){
        ApiError error = new ApiError(400,"Validation Error", "/api/v1/users");
        Map<String,String> validationError = new HashMap<>();
        for (FieldError fieldError: manve.getBindingResult().getFieldErrors()) {
            validationError.put( fieldError.getField(), fieldError.getDefaultMessage() );
        }
        error.setValidationsErrors( validationError );
        return error;
    }
}
