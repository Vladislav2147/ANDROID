package by.bstu.svs.fit.lr3.course;

import by.bstu.svs.fit.lr3.exception.PersonAlreadyOnCourseException;
import by.bstu.svs.fit.lr3.person.Person;
import lombok.Data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class Course {

    private String company;
    private String name;
    private List<Person> listeners;

    public Course() {
        listeners = new CopyOnWriteArrayList<>();
    }

    public Course(String company, String name, List<Person> listeners) {

        this.company = company;
        this.name = name;
        this.listeners = new CopyOnWriteArrayList<>(listeners);

    }

    public void add(Person person) throws PersonAlreadyOnCourseException {

        if (listeners.contains(person)) {
            throw new PersonAlreadyOnCourseException("person" + person.getFirstName() + " " +
                    person.getSecondName() + " is already on course!");
        }
        else {
            listeners.add(person);
        }

    }

    public boolean remove(Person person) {
        return listeners.remove(person);
    }

}
