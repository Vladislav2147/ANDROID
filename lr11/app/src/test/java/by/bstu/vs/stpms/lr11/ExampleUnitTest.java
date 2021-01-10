package by.bstu.vs.stpms.lr11;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void mainTest() {
        Polynom polynoms[] = {
                new PolynomialParser().parse("1"),
                new PolynomialParser().parse("2"),
                new PolynomialParser().parse("x"),
                new PolynomialParser().parse("x + 1"),
                new PolynomialParser().parse("x + 2"),
                new PolynomialParser().parse("2x"),
                new PolynomialParser().parse("2x+1"),
                new PolynomialParser().parse("2x+2"),
                new PolynomialParser().parse("x^2"),
                new PolynomialParser().parse("x^2 + 1"),
                new PolynomialParser().parse("x^2 + 2"),
                new PolynomialParser().parse("x^2 + x"),
                new PolynomialParser().parse("x^2 + x + 1"),
                new PolynomialParser().parse("x^2 + x + 2"),
                new PolynomialParser().parse("x^2 + 2x"),
                new PolynomialParser().parse("x^2 + 2x + 1"),
                new PolynomialParser().parse("x^2 + 2x + 2"),
                new PolynomialParser().parse("2x^2"),
                new PolynomialParser().parse("2x^2 + 1"),
                new PolynomialParser().parse("2x^2 + 2"),
                new PolynomialParser().parse("2x^2 + x"),
                new PolynomialParser().parse("2x^2 + x + 1"),
                new PolynomialParser().parse("2x^2 + x + 2"),
                new PolynomialParser().parse("2x^2 + 2x"),
                new PolynomialParser().parse("2x^2 + 2x + 1"),
                new PolynomialParser().parse("2x^2 + 2x + 2"),
        };

//        for (Polynom polynom : polynoms) {
//            Polynom p = polynom;
//            System.out.println("-----------------------------------------------");
//            System.out.println(p);
//
//            for (int j = 1; j < polynoms.length; j++) {
//                System.out.println("^" + j + ": " + p);
//                p = Polynom.result3(p.multiply(polynom));
//                p.normalize();
//
//            }
//        }

        Polynom p1 = new PolynomialParser().parse("2x^2 + 2x + 1");
        Polynom p2 = new PolynomialParser().parse("x^2 + 1");

        Polynom result = Polynom.result3(p1.multiply(p2));
        result.normalize();
        System.out.println(result);


//        List<Polynom> polynomList = new ArrayList<>();
//        for(int i = 0; i < polynoms.length; i++) {
//            System.out.print(i + 1 + ":\t");
//            for (int j = i; j < polynoms.length; j++) {
//                Polynom p = Polynom.result3(polynoms[i].multiply(polynoms[j]));
//                p.normalize();
//                polynomList.add(p);
//                System.out.print(p.add(new PolynomialParser().parse("0")) + "     ");
//            }
//            System.out.print("\n");
//            System.out.println("----------------------------------------------");
//        }
//        System.out.println("0:" + polynomList.stream().filter(polynom -> {
//            if (polynom != null) {
//                return polynom.toString().equals("0");
//            }
//            return false;
//        }).count());
//
//        System.out.println("1:" + polynomList.stream().filter(polynom -> {
//            if (polynom != null) {
//                return polynom.toString().equals("1");
//            }
//            return false;
//        }).count());
    }
}