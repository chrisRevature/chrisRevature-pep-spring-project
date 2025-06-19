package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register") 
    public ResponseEntity<Account> register(@RequestBody Account account) {
        if(!accountService.registerValid(account)) {
            return ResponseEntity.badRequest().body(null);
        }
        // Checks if the account username and password are correct before checking if it exists
        if(accountService.registerValid(account) && accountService.doesExist(account.getUsername())) {
            return ResponseEntity.status(409).body(null);
        }
        Account registeredAccount = accountService.register(account);
        return ResponseEntity.ok().body(registeredAccount);
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        if(accountService.login(account) == null) {
            return ResponseEntity.status(401).body(null);
        }
        Account loggedIn = accountService.login(account);
        return ResponseEntity.ok().body(loggedIn);
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(createdMessage);
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok().body(messageService.getAllMessages());
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> findMessageById(@PathVariable int messageId) {
        return ResponseEntity.ok().body(messageService.findByMessageId(messageId));
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteById(@PathVariable int messageId) {
        int output = messageService.deleteMessageById(messageId);
        if(output > 0) {
            return ResponseEntity.ok().body(output);
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int messageId, @RequestBody Message message) {
        if(messageService.updateMessageById(messageId, message.getMessageText()) == 1) {
            return ResponseEntity.ok().body(1);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesById(@PathVariable int accountId) {
        return ResponseEntity.ok().body(messageService.getAllMessagesById(accountId));
    }
}
