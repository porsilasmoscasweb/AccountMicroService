package reloadly.ms_01.controller;

import reloadly.ms_01.service.AccountService;
import reloadly.ms_01.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AccountController {

    @Autowired
    AccountService service;

    @GetMapping(value = "accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Account>> all() {
        List<Account> accounts = service.all();

        HttpHeaders headers = new HttpHeaders();
        headers.add("total", String.valueOf(accounts.size()));

        return new ResponseEntity<>(accounts, headers, HttpStatus.OK);
    }

    @GetMapping(value = "account/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> find(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @GetMapping(value = "account/findByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> findByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(service.findByName(name), HttpStatus.OK);
    }

    @PostMapping(value = "account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> create(@RequestBody Account ac) {
        boolean done = service.create(ac);

        if (done) {
            return new ResponseEntity<Boolean>(done, HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(HttpStatus.CONFLICT);
    }

    @PutMapping(value = "account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> update(@RequestBody Account ac) {
        boolean done = service.update(ac);

        if (done) {
            return new ResponseEntity<Boolean>(done, HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(done, HttpStatus.CONFLICT);
    }

    @DeleteMapping(value = "account/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        boolean done = service.delete(id);

        if (done) {
            return new ResponseEntity<Boolean>(done, HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(done, HttpStatus.CONFLICT);
    }

    @DeleteMapping(value = "account/deleteByName/{name}")
    public ResponseEntity<Boolean> deleteByName(@PathVariable("name") String name) {
        boolean done = service.deleteByName(name);

        if (done) {
            return new ResponseEntity<Boolean>(done, HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(done, HttpStatus.CONFLICT);
    }

}
