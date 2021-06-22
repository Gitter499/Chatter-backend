package controllers


/*
* 2021 Rafayel Amirkhanyan
*
* */

import com.beust.klaxon.Klaxon
import io.javalin.http.Context
import java.util.*

interface IAuthController {

    fun createUser(ctx: Context): Unit
    fun updateUser(ctx: Context): Unit
    fun getUser(ctx: Context): Unit
    fun deleteUser(ctx: Context): Unit
    fun verifyUser(ctx: Context): Unit
    fun banUser(ctx: Context): Unit

    /*
    I don't think this is required for now
    data class User(
         val email: String,
         val _id: String,
         val password: String
     )*/
}

object UserController : IAuthController {

    private data class User(
        val email: String,
        val _id: String,
        val password: String
    )

    private val users = hashMapOf<String, User>()

    override fun createUser(ctx: Context) {
        users[createID()] = ctx.body<User>()
    }

    override fun updateUser(ctx: Context) {
        TODO("Not yet implemented")
    }

    override fun getUser(ctx: Context) {
        val key = getTargetUserKey(ctx)
        ctx.status(200).result(users[key].toString())
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

    private fun createID(): String {
        return UUID.randomUUID().toString()
    }

    private fun getTargetUserKey(ctx: Context): String {
        var result: String = ""
        val json = Klaxon().parse<User>(ctx.body())
        users.forEach { (k, v) ->
            if (json != null) {
                if(v.equals(json.email)) {
                    result = k
                }
            }
        }
        return result
    }
}