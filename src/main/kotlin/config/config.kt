package config

import io.github.cdimascio.dotenv.dotenv

val dotenv = dotenv()
val PORT: Int = dotenv.get("PORT").toInt()
val config: Map<String, Any> = mapOf("Port" to PORT, "Host" to "Chatter")