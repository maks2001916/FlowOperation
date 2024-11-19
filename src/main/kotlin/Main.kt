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

    val passwords = mutableListOf<String>()
    val cards = mutableListOf<String>()
    val personsTwo = mutableListOf<String>()

    for (i in 0 .. 9) {
        passwords.add(generatePassword())
        cards.add(generateCard())
        personsTwo.add(persons.toList().get(i).name)
    }

    val combinedFlow = combineFlows(
        personsTwo.asFlow(),
        cards.asFlow(),
        passwords.asFlow()) { name, card, password ->
        FullPerson(name, card, password)
    }


    // Подписываемся и выводим результаты
    combinedFlow.collect { person ->
        println(person) // Выводим объекты Person
    }

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

fun <T1, T2, T3, R> combineFlows(
    first: Flow<T1>,
    second: Flow<T2>,
    third: Flow<T3>,
    combineFunction: (T1, T2, T3) -> R
): Flow<R> {
    return first.zip(second) { t1, t2 -> Pair(t1, t2) }
        .zip(third) { pair, t3 ->
            combineFunction(pair.first, pair.second, t3)
        }
}


