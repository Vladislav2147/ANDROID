package by.bstu.svs.fit.lr3.manager;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.person.Person;

public class Manager implements Action {

    //TODO lambdas
    //TODO sorting method and comparator

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
}
