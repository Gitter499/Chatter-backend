package config

import java.util.*


fun logger(message: String, type: LoggerType?) {
    when (type) {
        LoggerType.LOG -> println("LOG: $message, at ${Date()}")
        LoggerType.WARNING -> println("WARN: $message, at ${Date()}")
        LoggerType.ERROR -> {
            println("ERROR: $message, at ${Date()} \nStack Trace")

            Thread.getAllStackTraces().forEach {
                println(it)
            }
        }
        else -> println("MESSAGE: $message, at ${Date()}")
    }

}

enum class LoggerType {
    LOG,
    ERROR,
    WARNING
}