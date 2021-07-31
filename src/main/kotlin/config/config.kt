package config

import io.github.cdimascio.dotenv.dotenv

val dotenv = dotenv()
val PORT: Int = dotenv.get("PORT").toInt()
val ENCRYPT_KEY: String = dotenv.get("ENCRYPT_KEY").toString()

val config: Map<String, Any> = mapOf("Port" to PORT, "Host" to "Chatter", "JWT_ID" to ENCRYPT_KEY)