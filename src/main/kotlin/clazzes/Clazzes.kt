package clazzes

abstract class Person(var firstName: String, var lastName: String) {

    open fun getName(): String = "$firstName $lastName"
    abstract fun getAddress(): String;
}

class Student(fName: String, lName: String, val id: Int, val tutor: String = "") : Person(fName, lName) {

    override fun getAddress(): String {
        TODO("Not yet implemented")
    }

    override fun getName() = "Student #$id ${super.getName()}"
}

////////////////////////////////////////////////////////////////////////

sealed class PersonEvent {
    class Awake : PersonEvent()
    class Asleep : PersonEvent()
    class Eating(val food: String) : PersonEvent()
}

fun handlePersonEvent(event: PersonEvent) {
    when (event) {
        is PersonEvent.Awake -> println("person is awake!")
        is PersonEvent.Asleep -> println("person is asleep.")
        is PersonEvent.Eating -> println("person is eating ${event.food}")
    }
}

////////////////////////////////////////////////////////////////////////

data class Meeting(val id: Int, val location: String, val startTimestamp: Long) {

}

////////////////////////////////////////////////////////////////////////