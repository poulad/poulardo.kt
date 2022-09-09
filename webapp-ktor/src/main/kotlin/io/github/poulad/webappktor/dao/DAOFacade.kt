package io.github.poulad.webappktor.dao

import io.github.poulad.webappktor.models.Customer

interface DAOFacade {
    suspend fun allCustomers(): List<Customer>

    suspend fun addNewCustomer(firstName: String, lastName: String, email: String): Customer?
}
