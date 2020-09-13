package by.bstu.svs.fit.lr3;

import android.util.Log;

import org.junit.Test;

import java.util.Random;

import by.bstu.svs.fit.lr3.manager.Manager;

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
        for (int i = 0; i < 100; i++) {

            System.out.println(manager.personGenerator());

        }

    }
}