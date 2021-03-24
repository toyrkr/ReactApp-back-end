package com.toy.ReactApp.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.toy.ReactApp.error.ApiError;
import com.toy.ReactApp.shared.Views;
import com.toy.ReactApp.user.User;
import com.toy.ReactApp.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger( AuthController.class );

    @PostMapping("/api/v1/auth")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@RequestHeader(name = "Authorization", required = false) String authorization){
        log.info( "authorization ->" + authorization );
        if (authorization == null) {
            ApiError apiError = new ApiError(401,"Unauthorized request","/api/v1/auth");
            return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).body(apiError);
        }

        String base64encoded = authorization.split( " " )[1];
        String decoded = new String(Base64.getDecoder().decode( base64encoded )); // user1:p4ssword
        String parts[] = decoded.split( ":" ); // ["user1","p4ssword"]
        String username = parts[0];
        String password = parts[1];
        User inDb = userRepository.findUserByUsername( username );
        if(inDb == null) {
            ApiError apiError = new ApiError( 401, "Unauthorized request", "/api/v1/auth" );
            return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).body( apiError );
        }
        // username,displayname ve image
        /*Map<String,String> responseBody = new HashMap<>();
        responseBody.put( "username", inDb.getUsername() );
        responseBody.put( "displayname", inDb.getDisplayname() );
        responseBody.put( "image", inDb.getImage() );*/
        return ResponseEntity.ok(inDb);
    }
}
