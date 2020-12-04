package by.bstu.vs.stpms.lr11

import kotlin.jvm.JvmStatic

object StringDemo {
    @JvmStatic
    fun main(args: Array<String>) {
        val palindrome = "Dot saw I was Tod"
        val len = palindrome.length
        val tempCharArray = CharArray(len)
        val charArray = CharArray(len)
        for (i in 0 until len) {
            tempCharArray[i] = palindrome[i]
        }
        for (j in 0 until len) {
            charArray[j] = tempCharArray[len - 1 - j]
        }
        val reversePalindrome = String(charArray)
        println(reversePalindrome)
    }
}