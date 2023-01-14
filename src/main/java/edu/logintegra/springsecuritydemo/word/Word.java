package edu.logintegra.springsecuritydemo.word;

public class Word {

    private final String word;
    private final String bidWord;

    public Word(String word, String bidWord) {
        this.word = word;
        this.bidWord = bidWord;
    }

    public String getWord() {
        return word;
    }

    public String getBidWord() {
        return bidWord;
    }
}
