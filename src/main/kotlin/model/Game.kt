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

    private val player: Player = Player()
    private val computer: Computer = Computer()
    private var currentPlayer = player

    private var stage = Stage.PROMPT_FIRST
    private var gameDeck: List<Pair<Rank, Suit>> = listOf()
    private var tableCards: List<Pair<Rank, Suit>> = listOf()


    /**
     *  enum class representing each game stage
     *  @property Stage.PROMPT_FIRST
     *  @property Stage.PRINT_INITIAL
     *  @property Stage.GAME_LOOP
     *  @property Stage.FINAL_SCORE
     *  @property Stage.GAME_OVER
     */
    enum class Stage {
        PROMPT_FIRST,
        PRINT_INITIAL,
        GAME_LOOP,
        FINAL_SCORE,
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
                // prints game final scores
                Stage.FINAL_SCORE -> printFinalScoreBoard()
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
     *  @see currentPlayer
     *  @see S.NO_OPTION
     *  @see stage
     *  @see Stage.PRINT_INITIAL
     */
    private fun promptFirstPlayer() {
        while (true) {
            println("${S.FIRST_PROMPT_MSG}")
            when (readln().lowercase()) {
                "${S.YES_OPTION}" -> {
                    currentPlayer.assignFirst()
                    break
                }
                "${S.NO_OPTION}" -> {
                    currentPlayer.switchPlayer()
                    currentPlayer.assignFirst()
                    break
                }
            }
        }
        stage = Stage.PRINT_INITIAL
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
     *  updates each player's
     *  win state according
     *  to the current winning
     *  player
     *  @see currentPlayer
     *  @see Computer
     *  @see Player
     */
    fun updatePreviousWinner() {
        when (currentPlayer) {
            is Computer -> {
                computer.assignPreviousWinState(true)
                player.assignPreviousWinState(false)
            }
            else -> {
                player.assignPreviousWinState(true)
                computer.assignPreviousWinState(false)
            }
        }
    }

    /**
     *  prints the final score of the
     *  game. If players did not win any
     *  cards, the player who went first
     *  wins a perfect score, else the
     *  player with the most cards wins
     *  +3 points. Lastly, the game stage
     *  is set to GAME_OVER.
     *  @see tableCards
     *  @see Player
     *  @see Computer
     *  @see N.TWENTY_THREE
     *  @see N.THREE
     *  @see Stage.GAME_OVER
     */
    private fun printFinalScoreBoard() {
        // if cards left on table
        if (tableCards.isNotEmpty()) {
            // check who won last round
            val p = if (player.obtainPreviousWinState()) player else computer
            // update player cards with remaining table cards
            p.updateCardsWon(tableCards)
            // update player score
            p.updateScore(tableCards)
        }
        when {
            // if both players did not win any cards
            player.obtainCardsWon().isEmpty() && computer.obtainCardsWon().isEmpty() -> {
                // check who moved first
                val p = if (player.obtainMovedFirst()) player else computer
                // update to max points & cards won
                p.updatePointsWon(N.TWENTY_THREE.int)
                p.updateCardsWon(tableCards)
            }
            // if player has most cards
            player.obtainCardsWon().size > computer.obtainCardsWon().size -> {
                // add +3 to player score
                player.updatePointsWon(N.THREE.int)
            }
            // if computer has most cards
            computer.obtainCardsWon().size > player.obtainCardsWon().size -> {
                // add +3 to computer score
                computer.updatePointsWon(N.THREE.int)
            }
        }
        // print scores
        printScoreBoard()
        // update game stage
        stage = Stage.GAME_OVER
    }

    /**
     *  prints the round's winner
     *  (if stage during GAME_LOOP),
     *  & prints each player's running score
     */
    private fun printScoreBoard() {
        if (stage == Stage.GAME_LOOP) printWinner()
        printPlayerScores()
        printPlayerCardsWon()
        println()
    }

    /**
     *  prints name of winning round player
     *  @see S.WINNER_MSG
     *  @see currentPlayer
     */
    private fun printWinner() {
        println(
            "${S.WINNER_MSG}".replace(
                "${S.ASTERISK}",
                "${currentPlayer.javaClass.simpleName}"
            )
        )
    }

    /**
     *  prints the running score of both players
     *  @see S.STATS_MSG
     *  @see S.SCORE_MSG
     *  @see player
     *  @see computer
     */
    private fun printPlayerScores() {
        println(
            "${S.STATS_MSG}".replace(
                "${S.ASTERISK}${S.ASTERISK}${S.ASTERISK}",
                "${S.SCORE_MSG}"
            ).replace(
                "${S.ASTERISK}${S.ASTERISK}",
                "${player.obtainPointsWon()}"
            ).replace(
                "${S.ASTERISK}",
                "${computer.obtainPointsWon()}"
            )
        )
    }

    /**
     *  prints the number of cards won by both players
     *  @see S.STATS_MSG
     *  @see S.CARDS_MSG
     *  @see player
     *  @see computer
     */
    private fun printPlayerCardsWon() {
        println(
            "${S.STATS_MSG}".replace(
                "${S.ASTERISK}${S.ASTERISK}${S.ASTERISK}",
                "${S.CARDS_MSG}"
            ).replace(
                "${S.ASTERISK}${S.ASTERISK}",
                "${player.obtainCardsWon().size}"
            ).replace(
                "${S.ASTERISK}",
                "${computer.obtainCardsWon().size}"
            )
        )
    }


    /**
     *  Player class representing
     *  the main player.
     *  @property hand list of 1-6 cards
     *  @property cardsWon cumulative list of cards (max: 52)
     *  @property pointsWon cumulative points (max: 23)
     *  @property movedFirst indicates true if player moved first
     *  @property previousWinner indicates true if player won last round
     */
    private open inner class Player {

        private var hand = emptyList<Pair<Rank, Suit>>()
        private var cardsWon = emptyList<Pair<Rank, Suit>>()
        private var pointsWon = N.ZERO.int
        private var movedFirst = false
        private var previousWinner = false

        /**
         *  returns the player's hand
         *  @see hand
         */
        fun obtainHand(): List<Pair<Rank, Suit>> {
            return hand
        }

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
         *  returns the player's cards won
         *  @see cardsWon
         */
        fun obtainCardsWon(): List<Pair<Rank, Suit>> {
            return cardsWon
        }

        /**
         *  update's the player's internal
         *  cumulative list of previously won cards
         *  @param winningHand the list of cards just won
         *  @see cardsWon the cumulative list of won cards
         */
        fun updateCardsWon(winningHand: List<Pair<Rank, Suit>>) {
            cardsWon += winningHand
        }

        /**
         *  returns the player's points won
         *  @see pointsWon
         */
        fun obtainPointsWon(): Int {
            return pointsWon
        }

        /**
         *  updates the player's cumulative
         *  points won throughout the game
         *  @param points number of points to add
         *  @see pointsWon player's cumulative game points
         */
        fun updatePointsWon(points: Int) {
            pointsWon += points
        }

        /**
         *  assigns true if the
         *  player is moving first
         *  @see movedFirst
         */
        fun assignFirst() {
            movedFirst = true
        }

        /**
         *  returns true if player moved first
         *  @see movedFirst
         */
        fun obtainMovedFirst(): Boolean {
            return movedFirst
        }

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
         *  assigns the boolean value
         *  to the player's previousWin state
         *  @see previousWinner
         */
        fun assignPreviousWinState(boolean: Boolean) {
            previousWinner = boolean
        }

        /**
         *  returns the player's previousWin state
         *  @see previousWinner
         */
        fun obtainPreviousWinState(): Boolean {
            return previousWinner
        }

        /**
         *  draws a card from hand & updates the
         *  table cards. If hand is empty, the
         *  Computer deals an extra 6 cards to
         *  the player from game deck. If the game deck
         *  is empty, the game stage is automatically
         *  set to FINAL_SCORE & the game ends.
         *  @see computer
         *  @see stage
         *  @see Stage.FINAL_SCORE
         *  @see hand
         *  @see N.SIX
         */
        fun promptTurn() {
            printTable()
            if (isHandEmpty()) {
                if (isGameDeckEmpty()) stage = Stage.FINAL_SCORE
                else hand = computer.drawFromDeck(N.SIX.int)
            }
            if (!isHandEmpty()) obtainPlayerInput()
        }

        /**
         *  prints the number of cards
         *  on the table & prints the
         *  last top-most card Rank/Suit.
         *  prints "no cards" message
         *  if table is empty.
         *  @see S.TABLE_CARDS_MSG
         *  @see S.NO_OPTION
         *  @see S.TABLE_TOP_MSG
         *  @see tableCards
         */
        private fun printTable() {
            println(
                when {
                    tableCards.isEmpty() -> {
                        "${S.TABLE_CARDS_MSG}".replace(
                            "${S.ASTERISK}${S.ASTERISK}",
                            S.NO_OPTION.string.replaceFirstChar { it.titlecase() }
                        ).replace(
                            "${S.ASTERISK}",
                            "${S.EMPTY_STRING}"
                        )
                    }
                    else -> {
                        "${S.TABLE_CARDS_MSG}".replace(
                            "${S.ASTERISK}${S.ASTERISK}",
                            "${tableCards.size}"
                        ).replace(
                            "${S.ASTERISK}",
                            "${S.TABLE_TOP_MSG}"
                        ).replace(
                            "${S.ASTERISK}",
                            tableCards.last().asString()
                        )
                    }
                }
            )
        }

        /**
         *  returns true if
         *  player hand is empty
         *  @see hand
         */
        private fun isHandEmpty() = hand.isEmpty()

        /**
         *  returns true if
         *  game deck is empty
         *  @see gameDeck
         */
        private fun isGameDeckEmpty() = gameDeck.isEmpty()

        /**
         *  If Player, function displays Player messages
         *  & prompts user for input. If Computer,
         *  displays Computer messages & auto-moves
         *  for computer.
         *  @see hand
         *  @see N.ONE
         *  @see S.EXIT_OPTION
         *  @see stage
         *  @see Stage.FINAL_SCORE
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
                        stage = Stage.FINAL_SCORE
                        break
                    }
                }
            }
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
         *  draws 1 card from hand at
         *  the specified index &
         *  updates the player's hand.
         *  If the player's drawn card
         *  is similar to Rank/Suit as
         *  that of the Table Top Card,
         *  then the player wins the
         *  round & accumulates all
         *  table cards & +1 point
         *  for every [ A, 10, J, Q, K ]
         *  in the player's winning hand.
         *  If not, the round continues
         *  by simply updating the game's
         *  tableCards with the player's
         *  drawn card. A message is
         *  displayed during computer turn.
         *  @param index number from 1-6 used to draw card from hand
         *  @see hand
         *  @see currentPlayer
         *  @see S.COMPUTER_PLAY_MSG
         *  @see tableCards
         */
        fun drawFromHand(index: Int) {
            val n = index.dec()
            // draw card
            val drawnCard = hand[n]
            // update hand
            updateHand(index, n)
            // print message when computer draws card
            if (currentPlayer is Computer) {
                println(
                    "${S.COMPUTER_PLAY_MSG}".replace(
                        "${S.ASTERISK}",
                        drawnCard.asString()
                    )
                )
            }
            // check if winning hand
            if (isWinningDraw(drawnCard)) {
                // assign winning hand
                val winningHand = tableCards + drawnCard
                // update player cards won
                updateCardsWon(winningHand)
                // update player score
                updateScore(winningHand)
                // update the previous winner state
                updatePreviousWinner()
                // print scoreBoard
                printScoreBoard()
                // clear table cards
                tableCards = listOf()
            } else {
                tableCards += drawnCard
            }
        }

        /**
         *  updates the player's hand
         *  with a new list, omitting
         *  the previously drawn card
         *  @param index the index to start from
         *  @param n the index to end before
         *  @see hand
         *  @see N.ZERO
         */
        private fun updateHand(index: Int, n: Int) {
            val preCard = hand.subList(N.ZERO.int, n)
            val postCard = hand.subList(index, hand.lastIndex.inc())
            assignHand(preCard + postCard)
        }

        /**
         *  returns true if the player's drawn
         *  card has a similar Rank/Suit as
         *  the Top Table Card.
         *  @param drawnCard the card drawn from hand
         *  @see tableCards
         */
        private fun isWinningDraw(drawnCard: Pair<Rank, Suit>): Boolean {
            return if (tableCards.isNotEmpty()) {
                val topTableCard = tableCards.last()
                val isSameRank = topTableCard.first == drawnCard.first
                val isSameSuit = topTableCard.second == drawnCard.second
                isSameRank || isSameSuit
            } else false
        }

        /**
         *  update's the player's score
         *  by +1-pt for every
         *  [ A, 10, J, Q, K ] in the
         *  player's winning hand
         *  @param winningHand the list of cards just won
         */
        fun updateScore(winningHand: List<Pair<Rank, Suit>>) {
            winningHand.forEach {
                calculatePoint(it)
            }
        }

        /**
         *  adds +1 point for every
         *  [ A, 10, J, Q, K ] card
         *  @param card pair (Rank, Suit) representing game card
         *  @see pointsWon
         */
        private fun calculatePoint(card: Pair<Rank, Suit>) {
            pointsWon += when (card.first) {
                Rank.ACE,
                Rank.TEN,
                Rank.JACK,
                Rank.QUEEN,
                Rank.KING -> N.ONE.int
                else -> N.ZERO.int
            }
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
     *  @property hand list of 1-6 cards
     *  @property cardsWon cumulative list of cards (max: 52)
     *  @property pointsWon cumulative points (max: 23)
     *  @see Player
     */
    private inner class Computer : Player() {

        /**
         *  Computer prompt & auto-draw random
         *  @see N.ONE
         *  @see S.COMPUTER_PLAY_MSG
         */
        override fun obtainPlayerInput() {
            val random = (N.ONE.int..obtainHand().size).shuffled().last()
            drawFromHand(index = random)
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