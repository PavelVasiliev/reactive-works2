package ru.innotech.education.rxjava.service;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import ru.innotech.education.rxjava.model.Book;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TfIdfProcessor {

    public List<Pair<String, BigDecimal>> calculate(String fileName, int limit) {
        final RestClient client = new RestClient();
        final Map<String, Integer> names = new MyFileReader(fileName).read();
        String nameWithAuthor;

        List<Book> books = new ArrayList<>();
        for (String name : names.keySet()) {
            nameWithAuthor = name;
            String url = client.makeRequest(names.get(nameWithAuthor)).blockFirst();
            Book book;
            if (url != null) {
                book = createBook(url, nameWithAuthor);
                books.add(book);
            }
        }
        return convertResult(sortByValueWithLimit(countIDF(books), limit));
    }

    private List<Pair<String, BigDecimal>> convertResult(Map<String, Double> limitedTF_IDF) {
        return limitedTF_IDF.keySet().stream()
                .map(word ->
                        new ImmutablePair<>(
                                word, BigDecimal.valueOf(limitedTF_IDF.get(word))
                        )
                )
                .collect(Collectors.toList());
    }

    private Map<String, Double> countIDF(List<Book> books) {
        Map<String, Double> result = new HashMap<>();
        for (Book book : books) {
            Map<String, Double> bookTF = book.getBookTF();

            //counting TF-IDF for book
            for (String word : bookTF.keySet()) {
                double tempValue = bookTF.get(word) * giveLog10(word, books);
                if (result.containsKey(word)) {
                    double prevValue = result.get(word);
                    if (tempValue > prevValue) {
                        result.put(word, tempValue);
                    }
                } else {
                    result.put(word, tempValue);
                }
            }
        }
        return result;
    }

    private Map<String, Double> sortByValueWithLimit(Map<String, Double> map, int limit) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private Double giveLog10(String word, List<Book> books) {
        return Math.log10(books.size() * 1. / countBooksWithWordForLogarithm(word, books));
    }

    private int countBooksWithWordForLogarithm(String word, List<Book> books) {
        return (int) books.stream()
                .filter(book -> book.getBookTF().containsKey(word))
                .count();
    }

    private Book createBook(String url, String nameWithAuthor) {
        return new Book(url,
                nameWithAuthor.substring(0, nameWithAuthor.indexOf("by")),
                nameWithAuthor.substring(1, nameWithAuthor.indexOf("by")));
    }
}
