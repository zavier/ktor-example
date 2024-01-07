package com.github.zavier.dao

import com.github.zavier.models.Customer
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.config.units.EntryUnit
import org.ehcache.config.units.MemoryUnit
import org.ehcache.impl.config.persistence.CacheManagerPersistenceConfiguration
import java.io.File

class DAOFacadeCacheImpl(
    private val delegate: DAOFacade,
    storagePath: File
) : DAOFacade {

    private val cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        .with(CacheManagerPersistenceConfiguration(storagePath))
        .withCache(
            "customerCache",
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Int::class.javaObjectType,
                Customer::class.java,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(1000, EntryUnit.ENTRIES)
                    .offheap(10, MemoryUnit.MB)
                    .disk(100, MemoryUnit.MB, true)
            )
        )
        .build(true)

    private val customerCache = cacheManager.getCache("customersCache", Int::class.javaObjectType, Customer::class.java)
    override suspend fun allCustomers(): List<Customer> =
        delegate.allCustomers()

    override suspend fun customer(id: Int): Customer? =
        customerCache[id]
            ?: delegate.customer(id)
                .also { customer -> customerCache.put(id, customer) }

    override suspend fun addNewCustomer(firstName: String, lastName: String, email: String): Customer? =
        delegate.addNewCustomer(firstName, lastName, email)
            ?.also { customer -> customerCache.put(customer.id, customer) }

    override suspend fun editCustomer(id: Int, firstName: String, lastName: String, email: String): Boolean {
        customerCache.put(id, Customer(id, firstName, lastName, email))
        return delegate.editCustomer(id, firstName, lastName, email)
    }

    override suspend fun deleteCustomer(id: Int): Boolean {
        customerCache.remove(id)
        return delegate.deleteCustomer(id)
    }

}