package model

import data.Rank
import data.S
import data.Suit

/**
 *  holds the current game's state
 *  @param deck mutable list of pairs (Rank, Suit) representing 52 cards
 */
data class Game(
    private val deck: MutableList<Pair<Rank, Suit>> = mutableListOf()
){
    /**
     *  prints the entire deck
     *  of cards in a row on 1 line
     */
    fun printDeck() {
        println(deck.joinToString("${S.BLANK_SPACE}") { "${it.first}${it.second}" })
    }

    /**
     *  creates a mutable list of 52 pairs
     *  consisting of 13 Ranks & 4 Suits each.
     *  Shuffles deck in random order after
     *  it is finished creating it. Updates
     *  the internal deck upon completion.
     */
    private fun createDeck() {
        deck.apply {
            Rank.entries.forEach { rank ->
                Suit.entries.forEach { suit ->
                    add(Pair(rank, suit))
                }
            }
            shuffle()
        }
    }

    init {
        createDeck()
    }
}
