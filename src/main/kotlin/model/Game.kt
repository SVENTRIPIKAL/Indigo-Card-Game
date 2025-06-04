package model

import data.N
import data.Rank
import data.S
import data.Suit

/**
 *  returns the provided list of Pairs
 *  of Ranks/Suits as a 1-row String.
 */
fun List<Pair<Rank, Suit>>.asString(): String {
    return this.joinToString("${S.BLANK_SPACE}") { "${it.first}${it.second}" }
}


/**
 *  holds the current game's state
 *  @property player player class representing main player
 *  @property computer computer class & game card dealer
 *  @property gameDeck list of pairs (Rank, Suit) representing 52 cards
 *  @property tableCards list of cards currently on the game table
 */
class Game {

    private val player: Player = Player(listOf())
    private val computer: Computer = Computer(listOf())
    private var gameDeck: List<Pair<Rank, Suit>> = listOf()
    private var tableCards: List<Pair<Rank, Suit>> = listOf()


    /**
     *  main game loop.
     *  ends when user
     *  inputs "exit"
     */
    fun startGame() {

    }


    /**
     *  Player class representing
     *  the main player.
     *  @param hand list of 1-6 cards
     */
    private open inner class Player(private var hand: List<Pair<Rank, Suit>>) {

        /**
         *  sets the player's hand to
         *  the provided list of cards.
         *  @param cards list of 1-6 cards
         *  @see hand
         */
        fun setHand(cards: List<Pair<Rank, Suit>>) {
            hand = cards
        }

        /**
         *  draws 1 card from hand at
         *  the specified index, &
         *  updates the player's hand
         *  & the game's tableCards.
         *  @param index number from 1-6 used to draw card from hand
         *  @see hand
         *  @see tableCards
         */
        fun drawFromHand(index: Int) {
            val n = index.dec()
            val drawnCard = hand[n]
            val preCard = hand.subList(N.ZERO.int, n)
            val postCard = hand.subList(index, hand.lastIndex.inc())
            hand = preCard + postCard
            tableCards += drawnCard
        }

        /**
         *  returns true if
         *  player hand is empty
         *  @see hand
         */
        fun isHandEmpty() = hand.isEmpty()


        /**
         *  returns player hand as String
         *  @see hand
         *  @see asString
         */
        override fun toString(): String = hand.asString()
    }

    /**
     *  Computer class inheriting from the
     *  Player class. includes additional
     *  functions for dealing game cards.
     *  @param hand list of 1-6 cards
     *  @property dealCount number of games dealt (max: 4)
     *  @see Player
     */
    private inner class Computer(hand: List<Pair<Rank, Suit>>): Player(hand) {

        // number of games dealt (max: 4)
        private var dealCount = N.ZERO.int

        /**
         *  creates a list of 52 pairs consisting
         *  of 13 Ranks & 4 Suits each. Shuffles
         *  the game deck in a random order after
         *  it has been created. Updates the
         *  internal game deck upon completion.
         *  @see gameDeck
         *  @see Rank.entries
         *  @see Suit.entries
         */
        fun createDeck() {
            gameDeck = Rank.entries.flatMap { r ->
                Suit.entries.map { s ->
                    Pair(r, s)
                }
            }.shuffled()
        }

        /**
         *  increases dealCount by 1, updates
         *  tableCards with 4 cards, updates
         *  each player's hand with 6 cards,
         *  & shuffles the game deck afterwards.
         *  @see tableCards
         *  @see player
         *  @see computer
         */
        fun dealCards() {
            increaseDealCount()
            tableCards = drawFromDeck(n = N.FOUR.int)
            player.setHand(cards = drawFromDeck(N.SIX.int))
            computer.setHand(cards = drawFromDeck(N.SIX.int))
            shuffleDeck()
        }

        /**
         *  removes the first N cards from the
         *  top of the game deck & returns them.
         *  the internal game deck is updated to
         *  reflect the remaining cards.
         *  @param n number of cards to draw from game deck
         *  @see gameDeck
         */
        private fun drawFromDeck(n: Int): List<Pair<Rank, Suit>> {
            val requestedCards = gameDeck.subList(N.ZERO.int, n)
            val remainingCards = gameDeck.subList(n, gameDeck.lastIndex.inc())
            gameDeck = remainingCards
            return requestedCards
        }

        /**
         *  shuffles the order of the
         *  remaining cards in the
         *  current game deck. Updates the
         *  game deck upon completion.
         *  @see gameDeck
         */
        private fun shuffleDeck() {
            gameDeck = gameDeck.shuffled()
        }

        /**
         *  increases deal count by 1.
         *  @see dealCount
         */
        private fun increaseDealCount() = dealCount++

        /**
         *  returns true if deal
         *  limit (4) has been reached.
         *  @see dealCount
         */
        private fun isDealLimitReached() {
            dealCount == N.FOUR.int
        }
    }


    /**
     *  computer creates &
     *  deals game deck
     */
    init {
        computer.createDeck()
        computer.dealCards()
    }
}