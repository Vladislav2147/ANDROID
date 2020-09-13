package by.bstu.svs.fit.lr3.manager;

import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.person.Employee;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;
import by.bstu.svs.fit.lr3.person.University;


import java.io.File;
import java.util.List;
import java.util.Random;

public interface Action {

    static String info() {
        return "Interface Action contains methods for working with course";
    }

    Course generateCourse(String courseName, String organisationName, int studentsAmount);
    Course generateCourse(String courseName, String organisationName, String fileName);

    default Person personGenerator() {

        Person person;
        Random rand = new Random();

        if(rand.nextBoolean()) {
            person = new Employee();
            List<String> organisations = PersonManager.getOrganisations();
            String organisation = organisations.get(rand.nextInt(organisations.size()));
            ((Employee)person).setOrganisation(organisation);
        }
        else {
            person = new Student();
            University university = University.values()[rand.nextInt(University.values().length)];
            ((Student)person).setUniversity(university);
        }

        int age = rand.nextInt(PersonManager.maxAge - PersonManager.minAge) + PersonManager.minAge;
        List<String> firstNames = PersonManager.getFirstNames();
        List<String> secondNames = PersonManager.getSecondNames();

        person.setAge(age);
        person.setFirstName(firstNames.get(rand.nextInt(firstNames.size())));
        person.setSecondName(secondNames.get(rand.nextInt(secondNames.size())));

        return person;
    }
    
    class PersonManager {

        private static final int minAge = 16;
        private static final int maxAge = 99;


        public static String[] getFirstNames() {
            return new String[]{"Ivan", "Michael", "Vladimir", "Anton", "Nikolai", "Nikita", "Paul"};
        }

        public static String[] getSecondNames() {
            return new String[]{"Ivanov", "Michalov", "Vorobiev", "Lisunov", "Fursik", "Nikitin", "Golubev"};
        }

        public static String[] getOrganisations() {
            return new String[]{"ITechArt", "EPAM", "ITransition", "Wargaming", "Mail.ru", "Yandex"};
        }

    }
}
