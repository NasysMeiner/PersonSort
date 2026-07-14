package test.tests;

import model.Person;
import service.SearchService;
import test.Test;

public class SearchServiceTest extends Test {
    private SearchService searchService;
    private Person[] testData;
    
    @Override
    public void run() {
        searchService = new SearchService();
        testData = createTestData();
        
        testSearchByMail();
        testSearchByPassword();
        testSearchByName();
        testSearchByNonExistentName();
        testSearchWithEmptyCollection();
        
        if (getCode() != 0) {
            setSuccess();
            setMessage("All SearchService tests passed!");
        }
    }
    
    private Person[] createTestData() {
        return new Person[] {
            new Person.Builder().setName("Alex").setPassword("123456").setMail("mail@mail.com").build(),
            new Person.Builder().setName("Anna").setPassword("123456").setMail("mail@mail.com").build(),
            new Person.Builder().setName("Oleg").setPassword("sdfsdf23").setMail("mail@mail.com").build(),
            new Person.Builder().setName("Mariya").setPassword("asasdc").setMail("mail@mail.com").build(),
            new Person.Builder().setName("Kirill").setPassword("123456").setMail("mail@mail.com").build(),
            new Person.Builder().setName("Aleksandr").setPassword("sqweddf").setMail("mail@mail.com").build(),
            new Person.Builder().setName("Vlad").setPassword("cxcew3").setMail("mail@mail.com").build()
        };
    }
    
    private void testSearchByMail() {
        String target = "mail@mail.com";
        long expected = 7;
        long actual = searchService.searchElements(
            person -> person.getMail().equals(target), 
            testData
        );
        
        if (actual != expected) {
            setFail();
            setMessage("Search by mail failed! Expected: " + expected + 
                      ", but was: " + actual + " for target: " + target);
        }
    }
    
    private void testSearchByPassword() {
        String target = "123456";
        long expected = 3;
        long actual = searchService.searchElements(
            person -> person.getPassword().equals(target), 
            testData
        );
        
        if (actual != expected) {
            setFail();
            setMessage("Search by password failed! Expected: " + expected + 
                      ", but was: " + actual + " for target: " + target);
        }
    }
    
    private void testSearchByName() {
        String target = "Anna";
        long expected = 1;
        long actual = searchService.searchElements(
            person -> person.getName().equals(target), 
            testData
        );
        
        if (actual != expected) {
            setFail();
            setMessage("Search by name failed! Expected: " + expected + 
                      ", but was: " + actual + " for target: " + target);
        }
    }
    
    private void testSearchByNonExistentName() {
        String target = "Empty";
        long expected = 0;
        long actual = searchService.searchElements(
            person -> person.getName().equals(target), 
            testData
        );
        
        if (actual != expected) {
            setFail();
            setMessage("Search by non-existent name failed! Expected: " + expected + 
                      ", but was: " + actual + " for target: " + target);
        }
    }
    
    private void testSearchWithEmptyCollection() {
        Person[] emptyData = new Person[0];
        String target = "Oleg";
        long expected = 0;
        long actual = searchService.searchElements(
            person -> person.getName().equals(target), 
            emptyData
        );
        
        if (actual != expected) {
            setFail();
            setMessage("Search in empty collection failed! Expected: " + expected + 
                      ", but was: " + actual + " for target: " + target);
        }
    }
}