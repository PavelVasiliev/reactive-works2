package ru.innotech.education.rxjava.service;

import lombok.SneakyThrows;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toMap;

public class MyFileReader {
    private static final Pattern CSV_PATTERN = Pattern.compile("^(.*);(\\d+)$");

    private final String fileName;

    public MyFileReader(String fileName) {
        this.fileName = fileName;
    }

    @SneakyThrows
    public Map<String, Integer> read() {
        final URI uri = ClassLoader.getSystemResource(fileName).toURI();
        return Files.lines(Path.of(uri))
                .map(CSV_PATTERN::matcher)
                .filter(Matcher::find)
                .collect(toMap(m -> m.group(1), m -> Integer.valueOf(m.group(2))));
    }
}