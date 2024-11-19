package org.example

import kotlinx.coroutines.flow.*
import kotlin.random.Random

suspend fun main() {
    //var numbers = Array(10) { Random.nextInt(20) }.asFlow()
    //var sumSquareNumbers = numbers.reduce {a, b -> a + (b*b)}

    //println(sumSquareNumbers)

    var persons = mutableListOf(
        Person("Максим", 18),
        Person("Данил", 19),
        Person("Кирилл", 20),
        Person("Артём", 21),
        Person("Пётр", 22),
        Person("Алексей", 23),
        Person("Марина", 24),
        Person("Кристина", 25),
        Person("Алёна", 26),
        Person("Степан", 27)
    ).asFlow()

    persons.getPerson("А", 21)

    var passwords = mutableListOf<String>()
    var cards = mutableListOf<String>()

    println(generatePassword())
    println(generateCard())

}

suspend fun Flow<Person>.getPerson(first: String, age: Int) {
    this.toList().filter { it.name.first()==first.first() && it.age==age }.onEach { println(it) }
}

fun generatePassword(): String {
    var passwordSize = 0
    var password = ""
    while (passwordSize < 4) {
        password += Random.nextInt(9)
        passwordSize++
    }
    return password
}

fun generateCard(): String {
    val cardBuilder = StringBuilder()
    for (i in 0 until 16) {
        if (i > 0 && i % 4 == 0) {
            cardBuilder.append(" ")
        }
        cardBuilder.append(Random.nextInt(0, 10))
    }
    return cardBuilder.toString()
}
