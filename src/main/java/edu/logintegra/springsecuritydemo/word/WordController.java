package edu.logintegra.springsecuritydemo.word;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordController {

    @GetMapping("/upper/{word}")
    public Word prepareResponse(@PathVariable String word) {
        return new Word(word, word.toUpperCase());
    }
}
