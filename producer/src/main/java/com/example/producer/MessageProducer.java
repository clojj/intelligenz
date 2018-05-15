package com.example.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@Slf4j
@Component
@RestController
@EnableBinding(Source.class)
public class MessageProducer {

    private Source source;

    @Autowired
    public MessageProducer(Source source) {
        this.source = source;
    }

    @PostMapping("/upload")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        try {
            log.info("received file {}", file.getName());
            source.output().send(MessageBuilder.withPayload(file.getBytes()).build());
            redirectAttributes.addAttribute("success", "true");
            return new ModelAndView("redirect:/");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException exc) {
        return ResponseEntity.status(500).body("Error processing the image");
    }
}
