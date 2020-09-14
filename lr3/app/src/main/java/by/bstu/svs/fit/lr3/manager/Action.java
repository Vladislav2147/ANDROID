package by.bstu.svs.fit.lr3.manager;

import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.person.Employee;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;
import by.bstu.svs.fit.lr3.person.University;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public interface Action {

    static String info() {
        return "Interface Action contains methods for working with course";
    }

    Course generateCourse(File file) throws IOException;
    Course generateCourse(int studentsAmount);

    default Person generateListener() {

        Person listener;
        Random rand = new Random();

        if(rand.nextBoolean()) {
            listener = new Employee();
            String[] organisations = PersonManager.getOrganisations();
            String organisation = organisations[rand.nextInt(organisations.length)];
            ((Employee)listener).setOrganisation(organisation);
        }
        else {
            listener = new Student();
            University university = University.values()[rand.nextInt(University.values().length)];
            ((Student)listener).setUniversity(university);
        }

        int age = rand.nextInt(PersonManager.maxAge - PersonManager.minAge) + PersonManager.minAge;
        String[] firstNames = PersonManager.getFirstNames();
        String[] secondNames = PersonManager.getSecondNames();

        listener.setAge(age);
        listener.setFirstName(firstNames[rand.nextInt(firstNames.length)]);
        listener.setSecondName(secondNames[rand.nextInt(secondNames.length)]);

        return listener;
    }
    
    class PersonManager {

        private static final int minAge = 16;
        private static final int maxAge = 30;

        public static String[] getFirstNames() {
            return new String[]{"Ivan", "Michael", "Vladimir", "Anton", "Nikolai", "Hukuma", "Paul"};
        }

        public static String[] getSecondNames() {
            return new String[]{"Ivanov", "Michalov", "Vorobiev", "Lisunov", "Fursik", "Nikitin", "Golubev"};
        }

        public static String[] getOrganisations() {
            return new String[]{"ITechArt", "EPAM", "ITransition", "Wargaming", "Mail.ru", "Yandex"};
        }

        public static String[] getCourseNames() {
            return new String[]{"Java", "C#", "Python", "frontend", "backend"};
        }
    }
}
