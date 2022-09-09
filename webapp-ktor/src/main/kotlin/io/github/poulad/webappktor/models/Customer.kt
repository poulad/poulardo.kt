package io.github.poulad.webappktor.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)

val customerStorage = mutableListOf<Customer>()

object Customers : Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", 128)
    val lastName = varchar("last_name", 128)
    val email = varchar("email", 128)

    override val primaryKey = PrimaryKey(id)
}
