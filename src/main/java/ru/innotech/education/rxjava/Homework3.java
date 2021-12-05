package ru.innotech.education.rxjava;

import org.apache.commons.lang3.tuple.Pair;
import ru.innotech.education.rxjava.service.TfIdfProcessor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.joining;

public class Homework3 {
    public static void main(String[] args) {
        final String fileName = getFileName(args);
        final TfIdfProcessor processor = new TfIdfProcessor();

        final List<Pair<String, BigDecimal>> result = processor.calculate(fileName, 10);
        System.out.println(
                result
                        .stream()
                        .map(r -> r.getKey() + ": " + r.getValue().setScale(4, HALF_UP))
                        .collect(joining("\n"))
        );
    }

    private static String getFileName(String[] args) {
        if (args.length > 0) {
            return args[0];
        }
        final Scanner in = new Scanner(System.in);
        System.out.print("Enter fileName string: ");
        return in.nextLine();
    }
}
