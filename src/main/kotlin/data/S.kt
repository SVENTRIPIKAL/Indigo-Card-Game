package data

enum class S(val string: String) {
    ASTERISK("*"),
    BLANK_SPACE(" "),
    YES_OPTION("yes"),
    NO_OPTION("no"),
    EXIT_OPTION("exit"),
    TITLE_MSG("Indigo Card Game"),
    GAME_OVER_MSG("Game Over"),
    FIRST_PROMPT_MSG("Play first?"),
    INITIAL_CARDS_MSG("Initial cards on the table: *"),
    TABLE_CARDS_TOP_MSG("** cards on the table, and the top card is *"),
    IN_HAND_MSG("Cards in hand: *"),
    CHOOSE_CARD_MSG("Choose a card to play (1-*):"),
    COMPUTER_PLAY_MSG("Computer plays *"),
    ;
    override fun toString() = string
}