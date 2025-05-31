import data.Rank
import data.S
import data.Suit
import model.Game


fun main() {
    println(Rank.entries.joinToString("${S.BLANK_SPACE}"))
    println(Suit.entries.joinToString("${S.BLANK_SPACE}"))
    Game().printDeck()
}