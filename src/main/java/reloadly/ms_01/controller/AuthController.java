package reloadly.ms_01.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reloadly.ms_01.model.Account;
import reloadly.ms_01.service.AccountService;

import java.util.Date;
import java.util.stream.Collectors;

import static reloadly.ms_01.jwt.JWT.*;

@RestController
public class AuthController {

    @Autowired
    AccountService service;

    AuthenticationManager authManager;

    public AuthController(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> register(@RequestBody Account ac) {
        boolean done = service.register(ac);

        if (done) {
            return new ResponseEntity<Boolean>(done, HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(HttpStatus.CONFLICT);
    }

    @PostMapping(value = "confirm", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean confirm(@RequestParam("user") String user, @RequestParam("pwd") String pwd, @RequestParam("enabled") String enabled) {
        Account account = service.findByName(user);
        account.setEnabled(Boolean.parseBoolean(enabled));

        return service.update(account);
    }

    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestParam("user") String user, @RequestParam("pwd") String pwd) {
        try {
            Authentication autentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user, pwd));

            if (autentication.isAuthenticated()) {
                Account account = service.findByName(user);
                String token = null;

                if (account != null && account.isEnabled()) {
                    token = this.getToken(autentication);

                    account.setToken(token);
                    service.update(account);

                    return token;
                }

                return token;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return "account not found";
        }

        return "account not found";
    }

    protected String getToken(Authentication autentication) {
        // The body of the token includes the account and the roles to which it belongs, as well as the expiration date and the signature data.
        String token = Jwts.builder()
                .setIssuedAt(new Date()) // Creation date
                .setSubject(autentication.getName()) // User
                .claim("authorities", autentication.getAuthorities().stream() // Role
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setExpiration(new Date(System.currentTimeMillis() + TTL))
                .signWith(SignatureAlgorithm.HS512, KEY) // Key & Signature
                .compact(); // Token generation

        return token;
    }
}
