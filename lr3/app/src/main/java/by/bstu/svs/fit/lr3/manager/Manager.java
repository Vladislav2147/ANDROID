package by.bstu.svs.fit.lr3.manager;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import by.bstu.svs.fit.lr3.comparators.PersonAgeComparator;
import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;

public class Manager implements Action {


    public Optional<Course> getCourseFromFile(File file) {

        ObjectMapper objectMapper = new ObjectMapper();
        Course course;
        try {
            course = objectMapper.readValue(file, Course.class);
        } catch (IOException e) {
            Log.e("Manager", "generateCourse: " + e.getMessage());
            return Optional.empty();
        }
        return Optional.ofNullable(course);

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

    public void writeToFile(Course course, File file) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String json = objectMapper.writeValueAsString(course);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException ex) {
            Log.e("Manager", "writeToFile: " + ex.getMessage());
        }
    }

    public void sortByAgeThenBySecondName(Course course) {

        Comparator<Person> comparator = new PersonAgeComparator()
                .thenComparing(person -> person.getSecondName());
        Collections.sort(course.getListeners(), comparator);

    }

    public List<Student> getTopThreeStudentsByMark(Course course) {

        List<Student> studentsCollection = new ArrayList<>();
        course
                .getListeners()
                .stream()
                .filter((person -> person instanceof Student))
                .forEach(student -> studentsCollection.add((Student) student));

        studentsCollection.sort((a, b) -> b.getMark().compareTo(a.getMark()));

        return studentsCollection.subList(0,3);

    }

    public void addPersonToCourseInFile(File jsonFile, Person person) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonFile);
            ArrayNode listeners = (ArrayNode) root.findValue("listeners");
            listeners.addPOJO(person);
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(json);
            fileWriter.close();

            Log.d("test", json);
        } catch (IOException ex) {
            Log.e("Manager", "addPersonToCourseInFile: " + ex.getMessage());
        }

    }

}
