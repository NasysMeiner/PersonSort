package test.tests;

import model.Person;
import service.SearchService;
import test.Test;

public class SearchServiceTest extends Test {

    @Override
    public void run() {
        SearchService searchService = new SearchService();

        Person[] data = {new Person.Builder().setName("Alex").setPassword("123456").setMail("mail@mail.com").build(), 
            new Person.Builder().setName("Anna").setPassword("123456").setMail("mail@mail.com").build(),
            new Person.Builder().setName("Oleg").setPassword("sdfsdf23").setMail("mail@mail.com").build(),
            new Person.Builder().setName("Mariya").setPassword("asasdc").setMail("mail@mail.com").build(),
            new Person.Builder().setName("Kirill").setPassword("123456").setMail("mail@mail.com").build(),
            new Person.Builder().setName("Aleksandr").setPassword("sqweddf").setMail("mail@mail.com").build(),
            new Person.Builder().setName("Vlad").setPassword("cxcew3").setMail("mail@mail.com").build()
        };

        String targetMail = "mail@mail.com";
        long countMail = searchService.searchElements(person -> person.getMail().equals(targetMail), data);
        long expectedMail = 7;

        if(countMail != expectedMail) {
            setFail();
            setMessage("Not correct SearchService in mail!");
            return;
        }

        String targetPassword = "123456";
        long countPassword = searchService.searchElements(person -> person.getPassword().equals(targetPassword), data);
        long expectedPassword = 3;

        if(countPassword != expectedPassword) {
            setFail();
            setMessage("Not correct SearchService in password!");
            return;
        }

        String targetName = "Anna";
        long countName = searchService.searchElements(person -> person.getName().equals(targetName), data);
        long expectedName = 1;

        if(countName != expectedName) {
            setFail();
            setMessage("Not correct SearchService in name!");
            return;
        }
        
        String targetEmptyName = "Empty";
        long countEmptyName = searchService.searchElements(person -> person.getName().equals(targetEmptyName), data);
        long expectedEmptyName = 0;

        if(countEmptyName != expectedEmptyName) {
            setFail();
            setMessage("Not correct SearchService in empty name!");
            return;
        }

        data = new Person[0];
        String target = "Oleg";
        long count = searchService.searchElements(person -> person.getName().equals(target), data);
        long expected = 0;

        if(count != expected) {
            setFail();
            setMessage("Not correct SearchService in empty collection!");
            return;
        }

        setSuccess();
        setMessage("Correct work");
    }
    
}
