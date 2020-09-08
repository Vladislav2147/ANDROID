package by.bstu.shichko.fit.manager;

import by.bstu.shichko.fit.course.Course;
import by.bstu.shichko.fit.person.Person;
import by.bstu.shichko.fit.person.Student;

import java.io.File;
import java.util.List;

public interface Action {

    static String info() {
        return "Interface Action contains methods for working with course";
    }

    Course generateCourse(String courseName, String organisationName, int studentsAmount);
    Course generateCourse(String courseName, String organisationName, String fileName);

    default Person personGenerator() {
        return new Student();
    }
    
    class FileManager {

        public File getFileFromName(String filename) {
            return new File(filename);
        }

        public List<String> getFirstNames() {
            return List.of("Ivan", "Michael", "Vladimir", "Anton", "Nikolai", "Nikita", "Paul");
        }
        public List<String> getSecondNames() {
            return List.of("Ivanov", "Michalov", "Vorobiev", "Lisunov", "Fursik", "Nikitin", "Golubev");
        }

    }
}
