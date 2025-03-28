package com.jwt.jwttoken.web;



import com.jwt.jwttoken.domain.AccountCredentials;
import com.jwt.jwttoken.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/login")
public class LoginController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials accountCredentials){
        UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(accountCredentials.getUsername(), accountCredentials.getPassword());

        Authentication auth = authenticationManager.authenticate(creds);

        String jwts = jwtService.getToken(auth.getName());

       return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,"Token" + jwts).header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,"Authiorization")
               .build();
    }


}
