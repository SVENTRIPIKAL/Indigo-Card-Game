import data.ExitGameException
import model.Game

fun main() {
    val game = Game()
    while (true) {
        try {
            game.mainMenu()
        } catch (e: Exception) {
            when(e) {
                is ExitGameException -> {
                    println(e.message)
                    break
                }
                else -> println(e.message)
            }
        }
    }
}