package com.solution.hacktrail.service;

import com.solution.hacktrail.payload.AiPromptDTO;

public interface ChatService {

    String textGenerate(AiPromptDTO aiPromptDTO);
}
