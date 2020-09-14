package by.bstu.svs.fit.lr3.manager;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.person.Person;

public class Manager implements Action {


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
