package by.bstu.svs.fit.lr3.manager;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import by.bstu.svs.fit.lr3.comparators.PersonAgeComparator;
import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;

public class Manager implements Action {

    //TODO log

    @Override
    public Course generateCourse(File file) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Course course = objectMapper.readValue(file, Course.class);
        return course;

    }

    @Override
    public Course generateCourse(int studentsAmount) {

        Course course = new Course();
        Random rand = new Random();

        String[] courseNames = PersonManager.getCourseNames();
        String[] organisation = PersonManager.getOrganisations();

        course.setName(courseNames[rand.nextInt(courseNames.length)]);
        course.setCompany(organisation[rand.nextInt(organisation.length)]);

        List<Person> listeners = new ArrayList<>();

        for (int i = 0; i < studentsAmount; i++) {
            listeners.add(generateListener());
        }

        course.setListeners(listeners);
        return course;

    }

    public void sortByAgeThenBySecondName(Course course) {

        Comparator<Person> comparator = new PersonAgeComparator().thenComparing(person -> person.getSecondName());
        Collections.sort(course.getListeners(), comparator);

    }

    public List<Student> getTopThreeStudentsByMark(Course course) {

        List<Student> studentsCollection = new ArrayList<>();
        course.getListeners().stream().filter((person -> person instanceof Student)).forEach(student -> studentsCollection.add((Student) student));

        studentsCollection.sort((a, b) -> b.getMark().compareTo(a.getMark()));

        return studentsCollection.subList(0,3);

    }
}
