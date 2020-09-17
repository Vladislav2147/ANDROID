package by.bstu.svs.fit.lr3.course;

import java.util.ArrayList;
import java.util.List;

import by.bstu.svs.fit.lr3.exception.PersonAlreadyOnCourseException;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;
import by.bstu.svs.fit.lr3.person.University;
import lombok.Data;

@Data
public class Course {

    private String company;
    private String name;
    private List<Person> listeners;

    public Course() {
        listeners = new ArrayList<>();
    }

    public Course(String company, String name, List<Person> listeners) {

        this.company = company;
        this.name = name;
        this.listeners = new ArrayList<>(listeners);

    }

    public void add(Person person) throws PersonAlreadyOnCourseException {

        if (listeners.stream().anyMatch(listener -> listener.equals(person))) {
            throw new PersonAlreadyOnCourseException("person " + person.getFirstName() + " " +
                    person.getSecondName() + " is already on course!");
        }
        else {
            if(person instanceof Student && ((Student)person).getUniversity() != null) {
                University university = ((Student)person).getUniversity();
                university.addStudent();
            }
            listeners.add(person);
        }

    }

    public boolean remove(Person person) {
        return listeners.remove(person);
    }

}
