package chaitanya.shinde.store.controllers;

import chaitanya.shinde.store.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @RequestMapping("/")
    public Message sayHello() {
        return new Message("Hello World");
    }
}
