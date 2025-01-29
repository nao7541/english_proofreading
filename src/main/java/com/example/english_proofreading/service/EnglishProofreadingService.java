package com.example.english_proofreading.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class EnglishProofreadingService {
  private static final Logger logger = Logger.getLogger(EnglishProofreadingService.class.getName());

  private final ChatClient chatClient;
  private final String proofreadingPrompt;

  public EnglishProofreadingService(ChatClient.Builder builder, @Value("classpath:prompts/proofreading_prompt_html_japanese.txt") Resource promptResource) throws IOException {
    this.chatClient = builder.build();
    this.proofreadingPrompt = new String(Files.readAllBytes(Paths.get(promptResource.getURI())));
  }

  public String proofread(String message) {
    String prompt = proofreadingPrompt + "\n" + message;

    logger.info(String.format("Proofreading prompt: %s", prompt));
    
    return chatClient
            .prompt(prompt)
            .call()
            .content();
  }
}
