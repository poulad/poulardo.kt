package io.github.poulad.webappktor.dao

import io.github.poulad.sharedlibkt.model.Customer
import io.github.poulad.sharedlibkt.model.Customers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class DefaultDAOFacade : DAOFacade {
    override suspend fun allCustomers(): List<Customer> = dbQuery {
        Customers.selectAll().map(::adaptResultRowToCustomer)
    }

    override suspend fun addNewCustomer(firstName: String, lastName: String, email: String): Customer? =
        dbQuery {
            val insertStatement = Customers.insert {
                it[Customers.firstName] = firstName
                it[Customers.lastName] = lastName
                it[Customers.email] = email
            }

            if (insertStatement.insertedCount != 1) {
                // todo set up logging infra
                println("Failed to insert a new Customer. $insertStatement")
            }

            return@dbQuery insertStatement.resultedValues?.singleOrNull()?.let(::adaptResultRowToCustomer)
        }

    private fun adaptResultRowToCustomer(row: ResultRow) = Customer(
        id = row[Customers.id].toString(),
        firstName = row[Customers.firstName],
        lastName = row[Customers.lastName],
        email = row[Customers.email],
    )
}

val dao: DAOFacade = DefaultDAOFacade().apply {
    runBlocking {
        if (allCustomers().isEmpty()) {
            addNewCustomer("John", "Smith", "jsmith@example.org")
        }
    }
}
