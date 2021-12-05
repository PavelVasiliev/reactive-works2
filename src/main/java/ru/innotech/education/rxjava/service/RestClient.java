package ru.innotech.education.rxjava.service;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;

import java.net.URI;

public class RestClient {
    public Flux<String> makeRequest(Integer id) {
        return Flux.just(buildUri(id).toASCIIString());
    }

    @SneakyThrows
    private URI buildUri(Integer id) {
        return new URI("https://www.gutenberg.org/files/" + id + "/" + id + "-0.txt");
    }
}
