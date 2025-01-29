package com.example.english_proofreading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.english_proofreading.service.EnglishProofreadingService;

@Controller
public class EnglishProofreadingController {

  @Autowired
  private EnglishProofreadingService proofreadingService;

  // public EnglishProofreadingController(ChatClient.Builder builder) {
  //   this.chatClient = builder.build();
  // }

  // @GetMapping
  // public String chatPrompt(@RequestParam String message){
  //   return chatClient
  //           .prompt(message)
  //           .call()
  //           .content();
  // }

  @GetMapping("/")
  public String index(@RequestParam(value="message", required=false) String message, Model model) {
    if (message != null && !message.isEmpty()) {
      String result = proofreadingService.proofread(message);
      model.addAttribute("result", result);
    }
    
    return "home";
  }


}
