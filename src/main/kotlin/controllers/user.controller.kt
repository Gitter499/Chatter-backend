package controllers


/*
* 2021 Rafayel Amirkhanyan
*
* */

import com.beust.klaxon.Klaxon
import database.Users
import io.javalin.http.Context
import org.jetbrains.exposed.sql.*
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


}

object UserController : IAuthController {


    data class User(
        val email: String = "",
        val first_name: String = "",
        val last_name: String = "",
        val password: String? = ""
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
        val json = getData(ctx)
        val resource: String = ctx.pathParam("resource")

        /*
        * Separate function for changing password
        * */
        transaction {
            when (resource) {
                "email" -> Users.update({ Users.email eq json!!.email }) {
                    it[email] = json!!.email
                    println("Email changed")
                    ctx.status(200).json(object {
                        val message: String = "Email changed to ${json!!.email}"
                    })
                }
                "first_name" -> Users.update({ Users.email eq json!!.email }) {
                    it[first_name] = json!!.first_name
                    println("First name changed")
                }
                "last_name" -> Users.update({ Users.email eq json!!.email }) {
                    it[last_name] = json!!.last_name
                    println("Last name changed")
                }

                else -> ctx.status(200).json(object {
                    val success: Boolean = false
                    val message: String = "Resource does not exist"
                })
            }

        }
    }

    override fun getUser(ctx: Context) {
        val target: User? = getTargetUser(ctx)
        if (target != null) {
            ctx.json(object {
                val success: Boolean = true
                val email: String = target.email
                val first_name: String = target.first_name
                val last_name: String = target.last_name
            })
        } else {
            ctx.json(object {
                val success: Boolean = false
                val message: String = "Email does not exist"
            })
        }

    }

    override fun deleteUser(ctx: Context) {
        val json = getData(ctx)

        transaction {
            Users.deleteWhere {
                Users.email eq json!!.email
            }
        }
    }

    override fun verifyUser(ctx: Context) {
//
//        val email_username = config["EMAIL_USERNAME"]
//        val email_password = config["EMAIL_PASSWORD"]
//        val email = HtmlEmail()
//        var code: String = ""
//
//        for (i in 0..10) {
//            code += Math.random().toString()
//        }
//
//
//        email.setHtmlMsg(
//            """
//            <html>
//            <body>
//                <h1>
//                    Verify your email
//                </h1>
//
//                <h2>
//                    $code
//                </h2>
//            </body>
//            </html>
//        """.trimIndent()
//        )
//
//
//        email.setAuthentication(email, password)
//        email.send()


    }

    override fun banUser(ctx: Context) {
        /*
        * Still thinking on how to implement this
        * */
        TODO("Not yet implemented")
    }

    override fun loginUser(ctx: Context) {
        val json = getData(ctx)
        val cookie = ctx.cookie("auth-token")
        val valid: ValidateState = validate(ctx, json)
        val target = getTargetUser(ctx)

        if (target == null) {
            ctx.status(200).json(object {
                val success: Boolean = false
                val message: String = "Account does not exist, check if email is correct"
            })
        }

        if (valid != ValidateState.OK){
            ctx.status(200).json(object {
                val success: Boolean = false
                val message: String = "Reason: $valid"
            })
        }
        if (BCrypt.checkpw(target!!.password, json!!.password)) {

        }
    }

    private fun createID(): String {
        return UUID.randomUUID().toString()
    }

    private fun getTargetUser(ctx: Context): User? {
        val json = getData(ctx)
        return transaction {
            Users.select {
                Users.email eq json!!.email
            }.map { mapIt(it) }.firstOrNull()
        }
    }

    fun mapIt(it: ResultRow) = User(
        email = it[Users.email],
        first_name = it[Users.first_name],
        last_name = it[Users.last_name],
        password = it[Users.password]
    )

    // Local data classes


}


