package ru.innotech.education.rxjava.model;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Book {
    private static final Pattern WORDS_PATTERN = Pattern.compile("((\\b[^\\s]+\\b)((?<=\\.\\w).)?)");

    private String url;
    private String name;
    private String author;
    private String text;
    private Map<String, Double> bookTF;

    public Book(String url, String name, String author) {
        this.url = url;
        this.name = name;
        this.author = author;
        downloadText();
        calculateTFBook();
    }

    private synchronized void downloadText() {
        try {
            Document doc = Jsoup.connect(url).get();
            text = doc.body().text();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calculateTFBook() {
        bookTF = new HashMap<>();
        List<String> words = getWords();
        int totalWordsAmount = words.size();

        Map<String, Integer> map = countEachWordInText(words);
        map.keySet().
                forEach(word -> bookTF.put(word, (1. * map.get(word) / totalWordsAmount)));
    }

    private List<String> getWords(){
        List<String> words = new ArrayList<>();
        Matcher m = WORDS_PATTERN.matcher(text);
        while (m.find()){
            words.add(m.group());
        }
        return words;
    }

    private Map<String, Integer> countEachWordInText(List<String> words) {
        Map<String, Integer> wordsAmount = new HashMap<>();

        int value = 1;
        words.forEach(word -> {
            wordsAmount.put(word,
                    wordsAmount.containsKey(word) ? wordsAmount.get(word) + 1 : value);
        });
        return wordsAmount;
    }
}
