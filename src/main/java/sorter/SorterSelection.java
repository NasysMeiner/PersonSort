package sorter;

import model.Person;

public class SorterSelection{
    private final UserSorter nameSorter;
    private final UserSorter passwordSorter;
    private final UserSorter mailSorter;

    public SorterSelection(UserSorter nameSorter, UserSorter passwordSorter, UserSorter mailSorter) {
        this.nameSorter = nameSorter;
        this.passwordSorter = passwordSorter;
        this.mailSorter = mailSorter;
    }

    public void sortCollectionToName(Person[] collection) {
        nameSorter.sort(collection);
    }
    
    public void sortCollectionToPassword(Person[] collection) {
        passwordSorter.sort(collection);
    }

    public void sortCollectionToMail(Person[] collection) {
        mailSorter.sort(collection);
    }
}