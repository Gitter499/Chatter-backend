package routes

import config.config
import controllers.UserController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.util.RedirectToLowercasePathPlugin

fun run() {
    @Suppress("")
    val port: String = config["Port"].toString()

    val app: Javalin = Javalin.create { config ->
        config.registerPlugin(RedirectToLowercasePathPlugin())
    }.start(port.toInt())
    app.routes {
        path("auth/user") {
            path("create") {
                post(UserController::createUser)
            }
        }
    }
}