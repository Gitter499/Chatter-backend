package routes

import config.config
import controllers.UserController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.util.RedirectToLowercasePathPlugin

fun start() {
    val port: String = config["Port"].toString()

    val app: Javalin = Javalin.create { config ->
        config.registerPlugin(RedirectToLowercasePathPlugin())
    }.start(port.toInt())
    app.routes {
        path("auth/user") {
            post(UserController::getUser)
            path("create") {
                post(UserController::createUser)
            }
            path("update/:resource") {
                post(UserController::updateUser)
            }

            path("delete") {
                post(UserController::deleteUser)
            }
//            path("verify"){
//                post(UserController::verifyUser)
//            }
//            path("ban"){
//
//            }
        }
        path("chat") {

        }
    }
}