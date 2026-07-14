package service;

import java.util.function.Predicate;
import java.util.stream.Stream;
import model.Person;

public class SearchService {
    public SearchService() {}

    public long searchElements(Predicate<Person> predicate, Person[] data) {
        long count = Stream.of(data)
                    .parallel()
                    .filter(predicate)
                    .count();

        return count;
    }
}
