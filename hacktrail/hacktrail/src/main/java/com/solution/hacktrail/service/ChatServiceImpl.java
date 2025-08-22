package com.solution.hacktrail.service;

import com.solution.hacktrail.payload.AiPromptDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;  // final + RequiredArgsConstructor handles injection

    @Override
    public String textGenerate(AiPromptDTO aiPromptDTO) {

        String customizeMSG = String.format(
                "You are an assistant. " +
                        "Respond ONLY with a valid JSON object that contains exactly one field: " +
                        "'responseText' which is the AI's answer to the user's input. " +
                        "Do not include any extra text, explanations, or keys. " +
                        "User input: %s",
                aiPromptDTO.getPrompt()
        );

        String response = chatClient.prompt()
                .user(customizeMSG)
                .call()
                .content();

        if (response.contains("{")) {
            int start = response.indexOf("{");
            int end = response.lastIndexOf("}") + 1;
            response = response.substring(start, end);
        }

        return response;
    }
}
