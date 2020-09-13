package by.bstu.svs.fit.lr3;

import org.junit.Test;

import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.manager.Manager;
import by.bstu.svs.fit.lr3.person.Person;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {

        Manager manager = new Manager();

        Course course = manager.generateCourse(35);

        for (Person person :
                course.getListeners()) {
            System.out.println(person);
        }

    }
}