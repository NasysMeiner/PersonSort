package service;

import java.util.function.Predicate;
import java.util.stream.Stream;
import model.DataBaseService;
import model.Person;

public class SearchService {
    private final DataBaseService dataBaseService;

    public SearchService(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    public long searchElements(Predicate<Person> predicate) {
        Person[] data = dataBaseService.getAll();

        long count = Stream.of(data)
                    .parallel()
                    .filter(predicate)
                    .count();

        return count;
    }
}
