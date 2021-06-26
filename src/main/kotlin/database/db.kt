package database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


fun connectDB() {
    Database.connect("jdbc:sqlite:D:\\development-stuff\\ChatterBackend\\chatter.db", driver = "org.sqlite.JDBC")

    transaction {
        addLogger(StdOutSqlLogger)

        // Create tables/ implement models
        SchemaUtils.create(Users)


    }
}

// Models/Schema
object Users : Table("USERS") {

    val email: Column<String> = varchar("email", 255)
    val first_name: Column<String> = varchar("first_name", 60)
    val last_name: Column<String> = varchar("last_name", 60)
    val password: Column<String> = char("password", 60)
    val id: Column<Int> = integer("_id").autoIncrement()


    override val primaryKey: PrimaryKey? = PrimaryKey(id)
}


// Handlers

