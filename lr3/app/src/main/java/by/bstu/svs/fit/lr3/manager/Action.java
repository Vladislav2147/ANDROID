package by.bstu.svs.fit.lr3.manager;

import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;


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
