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

    public Person[] sortCollectionToName(Person[] collection) {
        return nameSorter.sort(collection);
    }
    
    public Person[] sortCollectionToPassword(Person[] collection) {
        return passwordSorter.sort(collection);
    }

    public Person[] sortCollectionToMail(Person[] collection) {
        return mailSorter.sort(collection);
    }
}