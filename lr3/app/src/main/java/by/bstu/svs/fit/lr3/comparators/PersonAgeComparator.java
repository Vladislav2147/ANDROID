package by.bstu.svs.fit.lr3.comparators;

import java.util.Comparator;

import by.bstu.svs.fit.lr3.person.Person;

public class PersonAgeComparator implements Comparator<Person> {

    @Override
    public int compare(Person person, Person t1) {

        if(person.getAge() > t1.getAge())
            return 1;
        else if(person.getAge() < t1.getAge())
            return -1;
        else
            return 0;

    }

}
