package com.github.zavier.dao

import com.github.zavier.models.Customer

interface DAOFacade {
    suspend fun allCustomers(): List<Customer>
    suspend fun customer(id: Int): Customer?

    suspend fun addNewCustomer(firstName: String, lastName: String, email: String): Customer?

    suspend fun editCustomer(id: Int, firstName: String, lastName: String, email: String): Boolean

    suspend fun deleteCustomer(id: Int): Boolean
}