package sorter;

import model.Person;

public class SorterSelection{
    private final UserSorter nameSorter;
    private final UserSorter passwordSorter;
    private final UserSorter mailSorter;

    private final UserSorter nameSorterDecorate;
    private final UserSorter passwordSorterDecorate;
    private final UserSorter mailSorterDecorate;

    public SorterSelection(UserSorter nameSorter, UserSorter passwordSorter, UserSorter mailSorter,
        UserSorter nameSorterDecorate, UserSorter passwordSorterDecorate, UserSorter mailSorterDecorate
    ) {
        this.nameSorter = nameSorter;
        this.passwordSorter = passwordSorter;
        this.mailSorter = mailSorter;

        this.nameSorterDecorate = nameSorterDecorate;
        this.passwordSorterDecorate = passwordSorterDecorate;
        this.mailSorterDecorate = mailSorterDecorate;
    }

    public Person[] sortCollectionToName(Person[] collection, boolean useDecorator) {
        UserSorter sorter = useDecorator ? nameSorterDecorate : nameSorter;

        return sorter.sort(collection);
    }
    
    public Person[] sortCollectionToPassword(Person[] collection, boolean useDecorator) {
        UserSorter sorter = useDecorator ? passwordSorterDecorate : passwordSorter;

        return sorter.sort(collection);
    }

    public Person[] sortCollectionToMail(Person[] collection, boolean useDecorator) {
        UserSorter sorter = useDecorator ? mailSorterDecorate : mailSorter;

        return sorter.sort(collection);
    }
}