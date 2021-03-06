package by.bstu.vs.stpms.lr11;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Polynom {

    final static String EMPTY_STRING = "";
    final static String POSITIVE_SIGN = " + ";
    final static String NEGATIVE_SIGN = " - ";
    final static String DEGREE_MARK_1 = "x";
    final static String DEGREE_MARK_NTH = "x^";

    public final Map<Integer, Integer> members
            = new TreeMap<Integer, Integer>(Collections.reverseOrder());

    public Polynom(Map<Integer, Integer> members) {
        this.members.putAll(members);
        deleteMembersWithZeroCoefficient();
    }

    /**
     * Сложение
     */
    public Polynom add(Polynom other) {
        Map<Integer, Integer> result = new TreeMap<Integer, Integer>();
        result.putAll(members);

        for (Integer currentKey : other.members.keySet()) {
            Integer resultValue = other.members.get(currentKey);
            Integer currentValue = result.get(currentKey);
            if (currentValue != null) {
                resultValue += currentValue;
            }
            result.put(currentKey, resultValue);
        }
        return new Polynom(result);
    }

    /**
     * Вычитание
     */
    public Polynom subtract(Polynom other) {
        Map<Integer, Integer> result = new TreeMap<Integer, Integer>();
        result.putAll(members);

        for (Integer currentKey : other.members.keySet()) {
            Integer currentValue = result.get(currentKey);
            if (currentValue != null) {
                Integer difference = currentValue - other.members.get(currentKey);
                result.put(currentKey, difference);
            } else {
                result.put(currentKey, other.members.get(currentKey));
            }
        }
        return new Polynom(result);
    }

    /**
     * Умножение
     */
    public Polynom multiply(Polynom other) {
        Map<Integer, Integer> result = new TreeMap<Integer, Integer>();

        for (Map.Entry<Integer, Integer> first : members.entrySet()) {
            for (Map.Entry<Integer, Integer> second : other.members.entrySet()) {
                Integer amountKey = first.getKey() + second.getKey();
                Integer productValue = first.getValue() * second.getValue();
                if (result.containsKey(amountKey)) {
                    productValue += result.get(amountKey);
                }
                result.put(amountKey, productValue);
            }
        }
        return new Polynom(result);
    }

    private void deleteMembersWithZeroCoefficient() {
        Iterator<Map.Entry<Integer, Integer>> iterator = members.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> pair = iterator.next();
            if (pair.getValue() == 0) {
                iterator.remove();
            }
        }
    }

    /**
     * Получение строкового представления знака одночлена в зависимости от знака
     * коэффициента и места размещения одночлена в многочлене. Если коэффициент
     * одночлена отрицательный - возвращается " - "; Если коэффициент одночлена
     * положительный и одночлен первый в многочлене - возвращается пустая
     * строка. Иначе - " + ";
     */
    private String viewSignMonomial(boolean isFirst, int coefficient) {
        final int MIN_POSITIVE_COEFFICIENT = 0;
        if (coefficient < MIN_POSITIVE_COEFFICIENT) {
            return NEGATIVE_SIGN;
        } else {
            return (isFirst) ? EMPTY_STRING : POSITIVE_SIGN;
        }
    }

    /**
     * Получение строкового представления коэффициента одночлена в зависимости
     * от степени. Если коэффициент равен единице или степень нулевая -
     * возвращается пустая строка. Иначе - возвращается коэффициент.
     */
    private String viewCoefficient(int coeff, int degree) {
        return ((coeff != 1) || (degree == 0))
                ? String.valueOf(coeff)
                : EMPTY_STRING;
    }

    /**
     * Получение строкового представления степени одночлена. Если степень
     * нулевая - возвращается пустая строка. Если степень 1 - возвращается "x".
     * Иначе - возвращается "x^" + степень.
     */
    private String viewDegree(int degree) {
        String result = EMPTY_STRING;
        if (degree != 0) {
            result = (degree == 1) ? DEGREE_MARK_1 : DEGREE_MARK_NTH + degree;
        }
        return result;
    }

    @Override
    public String toString() {
        if(members.values().stream().allMatch(value -> value == 0)) {
            return "0";
        }
        boolean isFirstMember = true;
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, Integer> current : members.entrySet()) {
            int currentCoeff = current.getValue();
            int currentDegree = current.getKey();
            builder.append(viewSignMonomial(isFirstMember, currentCoeff));
            builder.append(viewCoefficient(Math.abs(currentCoeff),
                    currentDegree));
            builder.append(viewDegree(currentDegree));
            isFirstMember = false;
        }
        return builder.toString();
    }

    public void normalize() {
        for (Map.Entry<Integer, Integer> entry : members.entrySet()) {
            if (entry.getValue() != 0) {
                entry.setValue(entry.getValue() % 3);
            }
            if (entry.getValue() < 0) {
                entry.setValue(entry.getValue() + 3);
            }
        }
    }
    public static Polynom result3(Polynom polynom) {
        while (polynom.members.containsKey(3) && polynom.members.get(3) != 0 || polynom.members.containsKey(4) && polynom.members.get(4) != 0 ) {
            polynom.normalize();
            if(polynom.members.containsKey(3) && polynom.members.get(3) != 0) {
                polynom = polynom.add(new PolynomialParser().parse("-x^3 - x^2 - 1"));
                polynom.normalize();
            }
            if(polynom.members.containsKey(4) && polynom.members.get(4) != 0) {
                polynom = polynom.add(new PolynomialParser().parse("-x^4 + x^2 + 2x + 1"));
                polynom.normalize();
            }
        }
        return polynom;
    }
}
