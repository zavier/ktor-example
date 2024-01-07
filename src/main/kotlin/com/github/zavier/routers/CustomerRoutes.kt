package com.github.zavier.routers

import com.github.zavier.models.Customer
import com.github.zavier.plugins.customerDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customer") {
        get {
            val customers = customerDao.allCustomers()
            if (customers.isNotEmpty()) {
                call.respond(customers)
            } else {
                call.respondText("No customers found", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest)

            val customer = customerDao.customer(Integer.parseInt(id)) ?: return@get call.respondText(
                "No customer with id $id",
                status = HttpStatusCode.NotFound)

            call.respond(customer)
        }
        post {
            val customer = call.receive<Customer>()
            customerDao.addNewCustomer(customer.firstName, customer.lastName, customer.email)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)

            val del = customerDao.deleteCustomer(Integer.parseInt(id))
            if (del) {
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}