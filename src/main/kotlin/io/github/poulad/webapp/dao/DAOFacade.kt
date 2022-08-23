package io.github.poulad.webapp.dao

import io.github.poulad.webapp.models.Customer

interface DAOFacade {
    suspend fun allCustomers(): List<Customer>

    suspend fun addNewCustomer(firstName: String, lastName: String, email: String): Customer?
}
