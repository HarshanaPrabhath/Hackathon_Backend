package com.solution.hacktrail.controller;

import com.solution.hacktrail.payload.AiPromptDTO;
import com.solution.hacktrail.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/getAnswer")
    public ResponseEntity<String> chat(@RequestBody AiPromptDTO aiPromptDTO) {
        String response = chatService.textGenerate(aiPromptDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
