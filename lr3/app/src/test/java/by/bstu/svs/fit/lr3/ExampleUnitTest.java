package by.bstu.svs.fit.lr3;

import org.junit.Test;

import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.exception.PersonAlreadyOnCourseException;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;
import by.bstu.svs.fit.lr3.person.University;

import static org.junit.Assert.assertEquals;

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

    @Test(expected = PersonAlreadyOnCourseException.class)
    public void personAlreadyExistsException() throws PersonAlreadyOnCourseException {

        Person person1 = new Student();

        person1.setFirstName("A");
        person1.setSecondName("B");
        person1.setAge(20);
        ((Student)person1).setMark(20);

        Person person2 = new Student();

        person2.setFirstName("A");
        person2.setSecondName("B");
        person2.setAge(20);

        Course course = new Course();

        course.add(person1);
        course.add(person2);

    }

    @Test
    public void universityAddStudent() throws PersonAlreadyOnCourseException {

        Student student = new Student();
        student.setUniversity(University.BSTU);

        assertEquals(0, University.BSTU.getStudentCount());

        Course course = new Course();
        course.add(student);

        assertEquals(1, University.BSTU.getStudentCount());

    }

}