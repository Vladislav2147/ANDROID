package by.bstu.vs.stpms.lr11

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.ArrayList
import kotlin.math.sqrt

//Task 2
//A
val b: Byte = 2
var i = 0
var s = "Hello kotlin"
//D
const val myConst = 0

fun main(args: Array<String>) {
//
//    //B
//    println(b.toInt().toString());
//    //C
//    println("Byte: $b");
//    println("Int: $i");
//    println("String: $s");
//    //E
//    val nullableInt: Int? = readLine()?.toIntOrNull()
//    println(nullableInt)

//    //Task 3
//    //A
//    println("task3A")
//    println(sum(6.1, 7.2, 8.3))
//    //B
//    println("task3B")
//    val validPassword = "12345678"
//    val invalidPassword = "12"
//    val emptyPassword = ""
//
//    val validEmail = "123@gmail.com"
//    val invalidEmail = "123"
//    val emptyEmail = ""
//
//    println(isValid(validEmail, validPassword))
//    println(isValid(invalidEmail, validPassword))
//    println(isValid(validEmail, invalidPassword))
//    println(isValid(validEmail, emptyPassword))
//    println(isValid(emptyEmail, validPassword))
//    //C
//    println("task3C")
//    println(isHoliday("25.12.2020"))
//    println(isHoliday("25.02.2020"))
//    //D
//    println("task3D")
//    try {
//        println(doOperation(1, 2, '/'))
//        println(doOperation(1, 2, 'm'))
//    } catch (e: Exception) {
//        println(e.message)
//    }
//    //E
//    println("task3E")
//    println(intArrayOf(1,2,3,4).indexOfMax())
//    println(intArrayOf(1,2,4,3).indexOfMax())
//    //F
//    println("task3F")
//    println("abababa".coincedense("ab"))
//
//    //Task 4
//    //A
//    println("task4A")
//    fun factorial1(n: Int): Int {
//        var factorial = 1;
//        for(i in 2..n) {
//            factorial *= i;
//        }
//        return factorial;
//    }
//    fun factorial2(n: Int): Int = if (n < 2) 1 else n * factorial2(n - 1)
//
//    println(factorial1(5))
//    println(factorial2(5))
//
//    //B
//    println("task4B")
//    fun isPrime(n: Int): Boolean {
//        for (i in 2..sqrt(n.toDouble()).toInt()) {
//            if (n % i == 0) return false
//        }
//        return true
//    }
//
//    val first20: ArrayList<Int> = ArrayList()
//    val second10 = IntArray(10)
//    var count = 0;
//    for (i in 1..1000) {
//        if(isPrime(i)) {
//            count++
//            when(count) {
//                in 1..20 -> first20.add(i)
//                in 21..30 -> second10[count % 10] = i
//            }
//        }
//    }
//    println("count: $count")
//    println("first 20 prime: $first20")
//    println("second 10 prime: ${second10.joinToString()}")
//
//    //Task 5
//    //A
//    println("task5A")
//    fun containsIn(collection: Collection<Int>, predicate: (Int)->Boolean) = collection.any(predicate)
//    val collection = listOf(1, 2, 3, 4, 5)
//    println("contains 0: ${containsIn(collection) { it == 0 }}")
//    println("contains even: ${containsIn(collection) { it % 2 == 0 }}")
//
//    //B
//    println("task5B")
//    val intList = mutableListOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)
//    intList += 5
//    intList.add(29)
//    intList
//            .distinct()
//            .filter { i -> i % 2 == 1 }
//            .filter(::isPrime)
//            .forEach(::println)
//
//    println("more than 20: ${intList.find { i -> i > 20 }}")
//
//    println("group by mod 10:")
//    intList.groupBy { i -> i % 10 }.forEach(::println)
//
//    val (first, second) = intList
//    println("first $first\tsecond $second")
//
//    //C
//    println("task5C")
//    var map = mapOf("Ivanov" to 15, "Petrov" to 20, "Lisunova" to 39, "Hukuma" to 21, "Andrey" to 35)
//    fun getMarks(map: Map<String, Int>): Map<String, Int> {
//        return map.mapValues {
//            when(it.value) {
//                40 -> 10
//                39 -> 9
//                38 -> 8
//                in 35..37 -> 7
//                in 32..34 -> 6
//                in 29..31 -> 5
//                in 25..28 -> 4
//                in 22..24 -> 3
//                in 19..21 -> 2
//                in 0..18 -> 1
//                else -> 0
//            }
//        }
//    }
//    map = getMarks(map)
//    println(map)
//
//    map
//            .toList()
//            .groupBy { it.second }
//            .mapValues { it.value.size }
//            .forEach { (mark, amount) -> println("mark: $mark, amount: $amount") }
//
//    val hasBadMarks = map.any { it.value < 4 }
//    println("is there any bad marks? $hasBadMarks")

}

fun sum(vararg doubles: Double) : Double {
    var sum = 0.0;
    for (double in doubles) {
        sum += double
    }
    return sum
}

fun isValid(email: String, password: String): Boolean {
    fun notNull(string: String): Boolean = string.isNotEmpty()

    if (!(notNull(email) && notNull(password))) return false

    val emailReg = Regex(pattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    val passwordReg = Regex(pattern = "\\s")
    val matchedEmail = emailReg.containsMatchIn(email)
    val matchedPassword = !passwordReg.containsMatchIn(password)
    val passwordLength: Boolean = !(password.length < 6 || password.length > 12)

    return matchedEmail && matchedPassword && passwordLength
}

enum class Holidays(val Date: String){
    Christmas("25.12"),
    VictoryDay("09.05"),
    NewYear("01.01")
}

fun isHoliday(date: String?): Boolean{
    var checkDate: Boolean = false
    var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    try{
        var dateObj = LocalDate.parse(date, formatter)
        when {
            date.equals(null) -> {
                print("Null caught!")
            }
            else -> {
                var formattedDate = dateObj!!.format(formatter)
                var dayMonth = formattedDate.substring(0, 5)
                when(dayMonth){
                    Holidays.Christmas.Date -> { println("It's holiday!")
                        checkDate = true }
                    Holidays.VictoryDay.Date -> { println("It's holiday!")
                        checkDate = true }
                    Holidays.NewYear.Date -> { println("It's holiday!")
                        checkDate = true }
                    else -> println("It's working day")
                }
            }
        }
    }
    catch (e: DateTimeParseException){
        print("Wrong format!")
    }
    return checkDate
}

fun doOperation(a: Int, b: Int, operation: Char): Double = when(operation) {
    '+' -> (a + b).toDouble();
    '-' -> (a - b).toDouble();
    '*' -> (a * b).toDouble();
    '/' -> a.toDouble() / b;
    else -> throw Exception("wrong operation!")
}

fun IntArray.indexOfMax(): Int? {
    val elem = this.maxOrNull() ?: return null
    return this.indexOf(elem)
}

fun String.coincedense(substring: String) = Regex(substring).findAll(this).toList().size