import database.connectDB
import routes.start

// Starting the API from Routes
fun main() {
    connectDB()
    start()

}