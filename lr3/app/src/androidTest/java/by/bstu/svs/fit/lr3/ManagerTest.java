package by.bstu.svs.fit.lr3;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.exception.PersonAlreadyOnCourseException;
import by.bstu.svs.fit.lr3.manager.Manager;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ManagerTest {

    Manager manager;
    File json;

    @Before
    public void init() {
        manager = new Manager();
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        json = new File(appContext.getExternalFilesDir(null), "testJson.json");
    }

    @Test
    public void generateCourse() {

        int studentsAmount = 25;
        Course generatedCourse = manager.generateCourse(studentsAmount);
        for (Person person :
                generatedCourse.getListeners()) {
            Log.i("Test", "generateCourse: " + person);
        }
        assertEquals(studentsAmount, generatedCourse.getListeners().size());

    }

    @Test
    public void sortByAgeThenBySecondNameTest() {

        Course course = new Course();

        Student maxAgeAName = new Student();
        maxAgeAName.setAge(35);
        maxAgeAName.setSecondName("A");

        Student maxAgeBName = new Student();
        maxAgeBName.setAge(35);
        maxAgeBName.setSecondName("B");

        Student maxAgeCName = new Student();
        maxAgeCName.setAge(35);
        maxAgeCName.setSecondName("C");

        Student midAge = new Student();
        midAge.setAge(25);
        midAge.setSecondName("D");

        Student minAge = new Student();
        minAge.setAge(18);
        minAge.setSecondName("E");

        try {
            course.add(midAge);
            course.add(maxAgeAName);
            course.add(minAge);
            course.add(maxAgeCName);
            course.add(maxAgeBName);
        }
        catch (PersonAlreadyOnCourseException ex) {
            Log.e("ManagerTest", "sortByAgeThenBySecondNameTest: " + ex.getMessage());
        }

        for (Person person :
                course.getListeners()) {
            Log.i("ManagerTest", "sortByAgeThenBySecondNameTest: " + person);
        }
        Log.i("ManagerTest", "---------------------sorting---------------------");
        manager.sortByAgeThenBySecondName(course);

        for (Person person :
                course.getListeners()) {
            Log.i("ManagerTest", "sortByAgeThenBySecondNameTest: " + person);
        }

        assertEquals(0, course.getListeners().indexOf(minAge));
        assertEquals(1, course.getListeners().indexOf(midAge));
        assertEquals(2, course.getListeners().indexOf(maxAgeAName));
        assertEquals(3, course.getListeners().indexOf(maxAgeBName));
        assertEquals(4, course.getListeners().indexOf(maxAgeCName));
    }

    @Test
    public void getTopThreeStudentsByMarkTest() {

        Course course = manager.generateCourse(100);

        Student bestStudent = new Student();
        bestStudent.setMark(10);
        course.getListeners().add(bestStudent);
        course.getListeners().add(bestStudent);
        course.getListeners().add(bestStudent);

        assertTrue(
                manager
                .getTopThreeStudentsByMark(course)
                .stream()
                .allMatch(student -> student.getMark().equals(10))
        );

    }

    @Test
    public void readWriteFileTest() {

        Course courseToFile = manager.generateCourse(20);
        manager.writeToFile(courseToFile, json);
        Course courseFromFile = manager.getCourseFromFile(json).orElse(new Course());

        assertEquals(courseToFile, courseFromFile);

    }
}
