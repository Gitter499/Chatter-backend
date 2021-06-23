package controllers


/*
* 2021 Rafayel Amirkhanyan
*
* */

import com.beust.klaxon.Klaxon
import database.Users
import io.javalin.http.Context
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import util.ValidateState
import util.validate
import java.util.*

interface IAuthController {

    fun createUser(ctx: Context): Unit
    fun updateUser(ctx: Context): Unit
    fun getUser(ctx: Context): Unit
    fun deleteUser(ctx: Context): Unit
    fun verifyUser(ctx: Context): Unit
    fun banUser(ctx: Context): Unit
    fun loginUser(ctx: Context): Unit

    /*
    I don't think this is required for now
    data class User(
         val email: String,
         val _id: String,
         val password: String
     )*/
}

object UserController : IAuthController {

    data class User(
        val email: String,
        val first_name: String,
        val last_name: String,
        val password: String,
    )

    private fun getData(ctx: Context): User? {
        return Klaxon().parse<User>(ctx.body())
    }


    override fun createUser(ctx: Context) {
        val json = getData(ctx)
        val hashedPassword = BCrypt.hashpw(json!!.password, BCrypt.gensalt(10))

        val valid: ValidateState = validate(ctx, json)

        if (valid != ValidateState.OK) {
            return
        }
        transaction {
            Users.insert {
                it[email] = json.email
                it[first_name] = json.first_name
                it[last_name] = json.last_name
                it[password] = hashedPassword
            }
        }


    }

    override fun updateUser(ctx: Context) {
        TODO("Not yet implemented")
    }

    override fun getUser(ctx: Context) {
        val key = getTargetUserKey(ctx)
        ctx.status(200).result("")
    }

    override fun deleteUser(ctx: Context) {
        TODO("Not yet implemented")
    }

    override fun verifyUser(ctx: Context) {
        TODO("Not yet implemented")
    }

    override fun banUser(ctx: Context) {
        TODO("Not yet implemented")
    }

    override fun loginUser(ctx: Context) {
        TODO("Not yet implemented")
    }

    private fun createID(): String {
        return UUID.randomUUID().toString()
    }

    private fun getTargetUserKey(ctx: Context): String? {
        var result: String = ""
        //val json = Klaxon().parse<User>(ctx.body())
        return null
    }
    // Local data classes


}