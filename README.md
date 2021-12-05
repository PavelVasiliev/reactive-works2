# Reactive Java Homework #3

![GitHub Classroom Workflow](../../workflows/GitHub%20Classroom%20Workflow/badge.svg?branch=master)

## Вычисление метрики TF-IDF

### Формулировка

1. На вход подается csv файл в формате `<название книги>;<id>`, где id – идентфиикатор книги на
   сайте [Project Gutenberg](https://www.gutenberg.org/).
2. С помощью [Netty HTTP Client](https://projectreactor.io/docs/netty/release/reference/index.html#http-client) скачать
   книги с сайта. Обрабатывать данные по мере их поступления, т.е. метод скачивания должен возвращать `Flux<String>`,
   где `String` – очередная порция данных.
3. С помощью Reactive Streams реализовать алгоритм вычисления TF-IDF для слов текста. Подсчет слов в документе и
   вычисление метрики TF-IDF можно разнести на два этапа:
    1. сначала вычислить список слов с количеством их вхождений, общее количество слов, вычислить метрику TF по каждому
       слову;
    2. вторым этапом пройти по всем документам и вычислить результирующую метрику TF-IDF.
4. Вывести TOP 10 слов, чья метрика TF-IDF самая высокая.

### Пояснения

Показатель TF-IDF (TF – term frequency, IDF - inverse document frequency) оценивает значимость слова в документе, на
основе данных о всей коллекции документов. Данная мера определяет вес слова за величину пропорциональную частоте его
вхождения в документ и обратно пропорциональную частоте его вхождения во всех документах коллекции.

Пример входного файла [books.csv](src/test/resources/books.csv).

Для разбиения текста на слова использовать regex `((\b[^\s]+\b)((?<=\.\w).)?)`.

Для хранения агрегированных данным можно использовать объекты, но при выборе структур данных следует учитывать, что
обработка ведется в разных потоках.

### Сборка и прогон тестов

```shell
./gradlew clean test
```

### Прием домашнего задания

Как только тесты будут успешно пройдены, в Github Classroom на dashboard появится отметка об успешной сдаче. Так же в
самом репозитории появится бейдж со статусом сборки.

## Ссылки

1. [TF-IDF](https://en.wikipedia.org/wiki/Tf%E2%80%93idf)
2. [TF-IDF с примерами кода: просто и понятно](http://nlpx.net/archives/57)
3. [Комбинирование статистического и лигвистического методов для извлечения двухсловных терминов из текста](http://apu.npomars.com/images/pdf/34_11.pdf)