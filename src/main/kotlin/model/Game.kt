package model

import data.N
import data.Rank
import data.S
import data.Suit

/**
 *  returns pair as Rank/Suit icon String.
 */
fun Pair<Rank, Suit>.asString(): String = "${this.first}${this.second}"

/**
 *  returns the provided list of Pairs
 *  of Ranks/Suits as a 1-row String.
 */
fun List<Pair<Rank, Suit>>.asString(): String {
    return this.joinToString("${S.BLANK_SPACE}") { it.asString() }
}


/**
 *  holds the current game's state
 *  @property player player class representing main player
 *  @property computer computer class & game card dealer
 *  @property currentPlayer indicates which player is currently in play
 *  @property stage indicates which stage the game is currently in play
 *  @property gameDeck list of pairs (Rank, Suit) representing 52 cards
 *  @property tableCards list of cards currently on the game table
 */
class Game {

    private val player: Player = Player(listOf())
    private val computer: Computer = Computer(listOf())
    private var currentPlayer = player

    private var stage = Stage.PROMPT_FIRST
    private var gameDeck: List<Pair<Rank, Suit>> = listOf()
    private var tableCards: List<Pair<Rank, Suit>> = listOf()


    /**
     *  enum class representing each game stage
     *  @property Stage.PROMPT_FIRST
     *  @property Stage.PRINT_INITIAL
     *  @property Stage.GAME_LOOP
     *  @property Stage.GAME_OVER
     */
    enum class Stage {
        PROMPT_FIRST,
        PRINT_INITIAL,
        GAME_LOOP,
        GAME_OVER,
    }


    /**
     *  main game loop.
     *  ends when user
     *  inputs "exit"
     *  @see S.TITLE_MSG
     *  @see stage
     *  @see Stage.entries
     *  @see currentPlayer
     *  @see S.GAME_OVER_MSG
     */
    fun startGame() {
        println("${S.TITLE_MSG}")
        while (true) {
            when (stage) {
                // prompts user for 1st player
                Stage.PROMPT_FIRST -> promptFirstPlayer()
                // print initial table cards message
                Stage.PRINT_INITIAL -> printInitial()
                // prompts user for a card to play from hand & computer auto-plays their turn
                Stage.GAME_LOOP -> {
                    currentPlayer.promptTurn()
                    currentPlayer.switchPlayer()
                }
                // ends game loop
                Stage.GAME_OVER -> break
            }
        }
        println("${S.GAME_OVER_MSG}")
    }

    /**
     *  prompts player for 1st player
     *  continuously until a valid
     *  "yes" or "no" is entered. If "no",
     *  the current player is updated &
     *  the game stage is set to PRINT_INITIAL.
     *  @see S.FIRST_PROMPT_MSG
     *  @see S.YES_OPTION
     *  @see stage
     *  @see Stage.PRINT_INITIAL
     *  @see S.NO_OPTION
     *  @see currentPlayer
     */
    private fun promptFirstPlayer() {
        println("${S.FIRST_PROMPT_MSG}")
        when(readln().lowercase()) {
            "${S.YES_OPTION}" -> {
                stage = Stage.PRINT_INITIAL
            }
            "${S.NO_OPTION}" -> {
                currentPlayer.switchPlayer()
                stage = Stage.PRINT_INITIAL
            }
        }
    }

    /**
     *  prints initial table cards & sets stage to GAME_LOOP
     *  @see S.INITIAL_CARDS_MSG
     *  @see tableCards
     *  @see stage
     *  @see Stage.GAME_LOOP
     */
    private fun printInitial() {
        println(
            "\n${S.INITIAL_CARDS_MSG}\n".replace(
                "${S.ASTERISK}",
                tableCards.asString()
            )
        )
        stage = Stage.GAME_LOOP
    }


    /**
     *  Player class representing
     *  the main player.
     *  @param hand list of 1-6 cards
     */
    private open inner class Player(var hand: List<Pair<Rank, Suit>>) {

        /**
         *  sets the player's hand to
         *  the provided list of cards.
         *  @param cards list of 1-6 cards
         *  @see hand
         */
        fun assignHand(cards: List<Pair<Rank, Suit>>) {
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
         *  returns true if
         *  game deck is empty
         *  @see gameDeck
         */
        fun isGameDeckEmpty() = gameDeck.isEmpty()

        /**
         *  switches the current
         *  player & ends their turn
         *  @see currentPlayer
         *  @see Computer
         *  @see Player
         */
        fun switchPlayer() {
            currentPlayer = when (this) {
                is Computer -> player
                else -> computer
            }
        }

        /**
         *  draws a card from hand & updates the
         *  table cards. If hand is empty, the
         *  Computer deals an extra 6 cards to
         *  the player from game deck. If the game deck
         *  is empty, the game stage is automatically
         *  set to GAME_OVER & the game ends.
         *  @see computer
         *  @see stage
         *  @see Stage.GAME_OVER
         *  @see hand
         *  @see N.SIX
         */
        fun promptTurn() {
            printTable()
            if (isHandEmpty()) {
                if (isGameDeckEmpty()) stage = Stage.GAME_OVER
                else hand = computer.drawFromDeck(N.SIX.int)
            }
            if (!isHandEmpty()) obtainPlayerInput()
        }

        /**
         *  prints the number of cards
         *  on the table & prints the
         *  last top-most card Rank/Suit
         *  @see S.TABLE_CARDS_TOP_MSG
         *  @see tableCards
         */
        private fun printTable() {
            println(
                "${S.TABLE_CARDS_TOP_MSG}".replace(
                    "${S.ASTERISK}${S.ASTERISK}",
                    "${tableCards.size}"
                ).replace(
                    "${S.ASTERISK}",
                    tableCards.last().asString()
                )
            )
        }

        /**
         *  If Player, function displays Player messages
         *  & prompts user for input. If Computer,
         *  displays Computer messages & auto-moves
         *  for computer.
         *  @see hand
         *  @see N.ONE
         *  @see S.EXIT_OPTION
         *  @see stage
         *  @see Stage.GAME_OVER
         */
        open fun obtainPlayerInput() {
            printInHand()
            val handSize = hand.size
            while (true) {
                printChooseCard()
                when (val x = readln().lowercase()) {
                    in "${N.ONE}".."$handSize" -> {
                        drawFromHand(x.toInt())
                        break
                    }
                    "${S.EXIT_OPTION}" -> {
                        stage = Stage.GAME_OVER
                        break
                    }
                }
            }
            println()
        }

        /**
         *  prints the player's in-hand cards
         *  @see S.IN_HAND_MSG
         *  @see hand
         */
        private fun printInHand() {
            println(
                "${S.IN_HAND_MSG}".replace(
                    "${S.ASTERISK}",
                    hand.asString()
                )
            )
        }

        /**
         *  prints the prompt for user input
         *  @see S.CHOOSE_CARD_MSG
         *  @see hand
         */
        private fun printChooseCard() {
            println(
                "${S.CHOOSE_CARD_MSG}".replace(
                    "${S.ASTERISK}",
                    "${hand.size}"
                )
            )
        }

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
     *  @see Player
     */
    private inner class Computer(hand: List<Pair<Rank, Suit>>): Player(hand) {

        /**
         *  Computer prompt & auto-draw random
         *  @see N.ONE
         *  @see hand
         *  @see S.COMPUTER_PLAY_MSG
         *  @see tableCards
         */
        override fun obtainPlayerInput() {
            val random = (N.ONE.int..hand.size).shuffled().last()
            drawFromHand(index = random)
            println(
                "${S.COMPUTER_PLAY_MSG}\n".replace(
                    "${S.ASTERISK}",
                    tableCards.last().asString()
                )
            )
        }

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
         *  updates tableCards with 4 cards,
         *  updates each player's hand with
         *  6 cards, & shuffles the game
         *  deck afterwards.
         *  @see tableCards
         *  @see N.FOUR
         *  @see player
         *  @see N.SIX
         *  @see computer
         */
        fun dealCards() {
            tableCards = drawFromDeck(n = N.FOUR.int)
            player.assignHand(cards = drawFromDeck(N.SIX.int))
            computer.assignHand(cards = drawFromDeck(N.SIX.int))
            shuffleDeck()
        }

        /**
         *  removes the first N cards from the
         *  top of the game deck & returns them.
         *  the internal game deck is updated to
         *  reflect the remaining cards.
         *  @param n number of cards to draw from game deck
         *  @see gameDeck
         *  @see N.ZERO
         */
        fun drawFromDeck(n: Int): List<Pair<Rank, Suit>> {
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