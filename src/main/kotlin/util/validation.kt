package util

import controllers.UserController
import controllers.UserController.mapIt
import database.Users
import io.javalin.http.Context
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun validateEmail(email: String): Boolean {
    val validator: Regex =
        Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    // emailregex.com, not crazy enough to actually make my own email regex

    return validator.matches(email)
}

fun validateUser(info: UserController.User?): UserController.User? {

    return transaction {
        Users.select {
            Users.email eq info!!.email
        }.map { mapIt(it) }.firstOrNull()
    }
}


fun validate(ctx: Context, info: UserController.User?): ValidateState {
    if (!validateEmail(info!!.email)) {
        ctx.status(200).json(object {
            val success: Boolean = false
            val message: String = "Email is invalid"
        })
        return ValidateState.INVALID_EMAIL
    }

    if (validateUser(info) != null) {
        ctx.status(200).json(object {
            val success: Boolean = false
            val message: String = "User already exists"
        })
        return ValidateState.USER_EXISTS
    }

    return ValidateState.OK
}

enum class ValidateState {
    INVALID_EMAIL,
    USER_EXISTS,
    OK
}
