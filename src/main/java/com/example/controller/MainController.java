package com.example.controller;

import com.example.domain.Message;
import com.example.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessageRepo repo;

    @GetMapping("/")
    public String getGreeting(@RequestParam(name = "name", required = false, defaultValue = "world") String name,
                              Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model){
        Iterable<Message> messages = repo.findAll();
        model.put("message", messages);
        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){
        Message message = new Message(text, tag);
        repo.save(message);
        Iterable<Message> messages = repo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, @RequestParam String tag, Map<String, Object> model){
        List<Message> byTag;
        if (filter==null && !filter.isEmpty()){
            byTag = repo.findByTag(filter);
        } else {
            byTag = (List<Message>) repo.findAll();
        }
        model.put("messages", byTag);
        return "main";
    }
}
