package io.github.poulad.webapp.dao

import io.github.poulad.webapp.models.Customer
import io.github.poulad.webapp.models.Customers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class DefaultDAOFacade : DAOFacade {
    override suspend fun allCustomers(): List<Customer> = dbQuery {
        Customers.selectAll().map(::adaptResultRowToCustomer)
    }

    private fun adaptResultRowToCustomer(row: ResultRow) = Customer(
        id = row[Customers.id].toString(),
        firstName = row[Customers.firstName],
        lastName = row[Customers.lastName],
        email = row[Customers.email],
    )
}
