package model

import data.*

/**
 *  holds the current game's state
 *  @param deck list of pairs (Rank, Suit) representing 52 cards
 */
data class Game(
    private var deck: List<Pair<Rank, Suit>> = listOf()
){
    /**
     *  displays game main menu
     *  & requests user input
     *  (reset | shuffle | get | exit)
     *  @throws ExitGameException
     *  @throws WrongActionException
     *  @throws InsufficientCardsException
     *  @throws InvalidNumberOfCardsException
     */
    fun mainMenu() {
        println("${S.MENU_MSG}")
        when (val input1 = readln().lowercase()) {
            "${S.RESET_OPTION}" -> {    // reset game deck
                createDeck()
                println("${S.RESET_SHUFFLE_MSG}".replace("${S.ASTERISK}", input1))
            }
            "${S.SHUFFLE_OPTION}" -> {  // shuffle game deck
                shuffleDeck()
                println("${S.RESET_SHUFFLE_MSG}".replace("${S.ASTERISK}", "${input1}d"))
            }
            "${S.GET_OPTION}" -> {      // get number of cards from top of deck
                println("${S.NUM_OF_CARDS_MSG}")
                when(val input2 = readln().toIntOrNull()) {
                     in (N.ONE.int..N.FIFTY_TWO.int) -> {
                         if (input2?.let { it <= deck.size } == true) {
                             // remove cards from deck & print to screen
                             val cards = getCards(input2)
                             printCards(cards)
                         }
                         else throw InsufficientCardsException("${S.INSUFFICIENT_CARDS_MSG}")
                     }
                    else -> throw InvalidNumberOfCardsException("${S.INVALID_NUM_OF_CARDS_MSG}")
                }
            }               // exit game loop
            "${S.EXIT_OPTION}" -> throw ExitGameException("${S.EXIT_MSG}")
                            // throw exception for wrong input
            else -> throw WrongActionException("${S.WRONG_ACTION_MSG}")
        }
    }

    /**
     *  creates a list of 52 pairs consisting
     *  of 13 Ranks & 4 Suits each. Shuffles
     *  the deck in a random order after
     *  it has been created. Updates the
     *  internal deck upon completion.
     */
    private fun createDeck() {
        deck = Rank.entries.flatMap { r ->
            Suit.entries.map { s ->
                Pair(r, s)
            }
        }.shuffled()
    }

    /**
     *  shuffles the order of the
     *  remaining cards in the
     *  current game deck. Updates the
     *  internal deck upon completion.
     */
    private fun shuffleDeck() {
        deck = deck.shuffled()
    }

    /**
     *  removes the first N cards from the
     *  top of the deck & returns them.
     *  the internal deck is updated to
     *  reflect the remaining cards.
     */
    private fun getCards(n: Int): List<Pair<Rank, Suit>> {
        val requestedCards = deck.subList(N.ZERO.int, n)
        val remainingCards = deck.subList(n, deck.lastIndex.inc())
        deck = remainingCards
        return requestedCards
    }

    /**
     *  prints the provided deck
     *  of cards in a row on 1 line.
     */
    private fun printCards(cards: List<Pair<Rank, Suit>>) {
        println(cards.joinToString("${S.BLANK_SPACE}") { "${it.first}${it.second}" })
    }


    init {
        createDeck()
    }
}