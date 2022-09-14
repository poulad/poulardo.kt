package io.github.poulad.sharedlibkt.cache

import io.github.poulad.sharedlibkt.model.Customer

interface RedisRepository {
    suspend fun loadAllCustomers(): List<Customer>

    suspend fun getCustomerById(id: String): Customer?

    suspend fun addNewCustomer(customer: Customer)

    suspend fun subscribeToCustomersChannel()

    suspend fun publishToCustomersChannel(message: String)
}
