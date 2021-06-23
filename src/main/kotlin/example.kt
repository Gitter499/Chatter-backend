import config.config
import io.javalin.Javalin


val PORT: String = config["Port"].toString()


fun main(args: Array<String>) {
    val app: Javalin = Javalin.create().start(PORT.toInt())
    app.get("/") { ctx ->
        ctx.status(200).json(object {
            val message = "Hello From Kotlin"
        })
    }

    app.get("/param/:param-name") { ctx ->
        ctx.status(200).json(object {
            val pathName: String = ctx.pathParam("param-name")
            val message: String = "Success!"
        })
    }

    // Websockets

    app.ws("/ws/") { ws ->
        ws.onConnect { ctx -> println("Connected! Host: ${ctx.host()}")
        }
        ws.onMessage { ctx ->
            println(ctx.message().toString())
        }
        ws.onClose {
            println("Connection closed")
        }
        ws.onError { ctx ->
            println("An error occured: ${ctx.error().toString()}")
        }
    }

}