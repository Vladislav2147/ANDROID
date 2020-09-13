package by.bstu.svs.fit.lr3.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.person.Person;

public class Manager implements Action {

    @Override
    public Course generateCourse(String courseName, String organisationName, File file) {
        return null;
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
}
